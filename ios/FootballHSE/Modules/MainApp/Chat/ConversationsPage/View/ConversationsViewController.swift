//
//  ConversationsViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class ConversationsViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var messageLabel: UILabel!
    @IBOutlet weak var messageImageView: UIImageView!
    @IBOutlet weak var refreshButton: UIButton!

    @IBOutlet weak var tableView: UITableView!
    private let refreshControl = UIRefreshControl()

    // MARK: Public Properties

    var output: ConversationsViewOutput?

    // MARK: Private Properties

    var data: [ConversationDisplayModel] = [] {
        didSet {
            tableView.reloadData()
        }
    }

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
        output?.viewDidLoad()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        output?.viewDidAppear()
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }

    // MARK: Private

    private func setupView() {
        setupTableView()
        setupRefreshButton()

        messageLabel.isHidden = true
        messageImageView.isHidden = true
    }

    private func setupRefreshButton() {
        refreshButton.addTarget(self, action: #selector(refresh), for: .touchUpInside)
    }

    private func setupTableView() {
        tableView.register(
            UINib(nibName: String(describing: ConversationCell.self), bundle: nil),
            forCellReuseIdentifier: ConversationCell.identifier
        )
        tableView.register(
            UINib(nibName: String(describing: SupportCell.self), bundle: nil),
            forCellReuseIdentifier: SupportCell.identifier
        )
        tableView.dataSource = self
        tableView.delegate = self

        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        tableView.addSubview(refreshControl)

        tableView.showsVerticalScrollIndicator = false
    }

    // MARK: Actions

    @objc private func refresh(_ sender: AnyObject) {
        output?.viewStartRefreshing()
    }
}

// MARK: - ConversationsViewInput

extension ConversationsViewController: ConversationsViewInput {

    func setupLoadingState() {
        if !refreshControl.isRefreshing {
            refreshControl.beginRefreshing()
        }
        tableView.isHidden = false
        messageLabel.isHidden = true
        messageImageView.isHidden = true
        refreshButton.isHidden = true
    }

    func setupDataState(with data: [ConversationDisplayModel]) {
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }
        self.data = data
        tableView.isHidden = false
        messageLabel.isHidden = true
        messageImageView.isHidden = true
        refreshButton.isHidden = true
    }

    func setupErrorState() {
        setupEmptyState()
        let alert = UIAlertController(title: "Ошибка сети", message: "Проверьте подключение и повторите попытку", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }

    func setupEmptyState() {
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }

        tableView.isHidden = true
        messageLabel.isHidden = false
        messageImageView.isHidden = false
        refreshButton.isHidden = false
    }
}

// MARK: - UITableViewDelegate

extension ConversationsViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let conversation = data[indexPath.row]
        output?.wantsToOpenConversation(
            phoneNumber: conversation.phoneNumber,
            name: conversation.name,
            image: conversation.photo,
            conversationID: conversation.id
        )
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

// MARK: - UITableViewDataSource

extension ConversationsViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if data[indexPath.row].isSupport {
            let cell = tableView.dequeueReusableCell(
                withIdentifier: SupportCell.identifier,
                for: indexPath)
            guard let cell = cell as? SupportCell else {
                return cell
            }
            let chat = data[indexPath.row]
            cell.configure(data: chat)
            return cell
        }

        let cell = tableView.dequeueReusableCell(
            withIdentifier: ConversationCell.identifier,
            for: indexPath)
        guard let cell = cell as? ConversationCell else {
            return cell
        }
        let chat = data[indexPath.row]
        cell.configure(data: chat)
        return cell
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        data.count
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        1
    }
}
