//
//  MyApplicationsPageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class MyApplicationsPageViewController: UIViewController {
    
    // MARK: Private data structures

    private enum Constants {
        static let noApplicationsString = "У вас пока нет активных заявок"
        static let errorString = "Что-то пошло не так, попробуйте ещё раз"
    }

    // MARK: Outlets

    @IBOutlet weak var messageLabel: UILabel!
    @IBOutlet weak var messageImageView: UIImageView!
    @IBOutlet weak var refreshButton: UIButton!

    @IBOutlet weak var tableView: UITableView!
    private let refreshControl = UIRefreshControl()

    // MARK: Public Properties

    var output: MyApplicationsPageViewOutput?

    var data: [ProfilePlayerApplicationDisplayModel] = [] {
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

    // MARK: Private

    private func setupView() {
        setupTableView()
        setupRefreshButton()
        navigationItem.title = "Мои заявки"
    }

    private func setupRefreshButton() {
        refreshButton.addTarget(self, action: #selector(refresh), for: .touchUpInside)
    }

    private func setupTableView() {
        tableView.register(
            UINib(nibName: String(describing: MyApplicationCell.self), bundle: nil),
            forCellReuseIdentifier: MyApplicationCell.identifier
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


// MARK: - MyApplicationsPageViewInput

extension MyApplicationsPageViewController: MyApplicationsPageViewInput {

    func setupLoadingState() {
        if !refreshControl.isRefreshing {
            refreshControl.beginRefreshing()
        }
        tableView.isHidden = false
        messageLabel.isHidden = true
        messageImageView.isHidden = true
        refreshButton.isHidden = true
    }

    func setupDataState(with data: [ProfilePlayerApplicationDisplayModel]) {
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
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }

        tableView.isHidden = true
        messageLabel.isHidden = false
        messageImageView.isHidden = false
        refreshButton.isHidden = false

        messageLabel.text = Constants.errorString
        messageImageView.image = UIImage(named: "ErrorStateInProfile")
    }

    func setupEmptyState() {
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }

        tableView.isHidden = true
        messageLabel.isHidden = false
        messageImageView.isHidden = false
        refreshButton.isHidden = false

        messageLabel.text = Constants.noApplicationsString
        messageImageView.image = UIImage(named: "EmptyStateInProfile")
    }
}

// MARK: - UITableViewDelegate

extension MyApplicationsPageViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }

    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        12
    }

    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        UIView()
    }
}

// MARK: - UITableViewDataSource

extension MyApplicationsPageViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(
            withIdentifier: MyApplicationCell.identifier,
            for: indexPath)
        guard let searchCell = cell as? MyApplicationCell else {
            return cell
        }
        let application = data[indexPath.section]
        searchCell.configure(
            tournaments: application.tournaments,
            roles: application.playerPosition
        )
        return searchCell
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        1
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        data.count
    }
}
