//
//  SearchTeamsPageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class SearchTeamsPageViewController: UIViewController {
    
    // MARK: Private data structures

    private enum Constants {
        static let textFieldCornerRadius: CGFloat = 12
        static let noApplicationsString = "Заявок пока нет"
        static let noResultsString = "По вашему запросу ничего не нашлось"
    }
    
    // MARK: Outlets

    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var filterButton: UIButton!
    @IBOutlet weak var createApplicationButton: HSECreateApplicationButton!

    @IBOutlet weak var messageLabel: UILabel!
    @IBOutlet weak var messageImageView: UIImageView!

    @IBOutlet weak var tableView: UITableView!
    private let refreshControl = UIRefreshControl()

    // MARK: Public Properties

    var output: SearchTeamsPageViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        hideKeyboardWhenTappedAround()
        setupView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }

    // MARK: Private

    private func setupView() {
        filterButton.setTitle("", for: .normal)
        createApplicationButton.setTitle("Создать заявку на поиск команды", for: .normal)
        setupSearchBar()
        setupTableView()
        setupMessageState()

        messageLabel.isHidden = true
        messageImageView.isHidden = true
    }

    private func setupSearchBar() {
        if let textField = searchBar.value(forKey: "searchField") as? UITextField {
            textField.backgroundColor = UIColor(named: "BaseSurface1")
            textField.font = .systemFont(ofSize: 16, weight: .medium)
            textField.tintColor = UIColor(named: "TextAndIconsSecondary")
            textField.bounds.size.height = 40
            let backgroundView = textField.subviews.first
            backgroundView?.layer.cornerRadius = 10
            backgroundView?.layer.masksToBounds = true
        }
    }

    private func setupTableView() {
        tableView.register(
            UINib(nibName: String(describing: SearchTeamsCell.self), bundle: nil),
            forCellReuseIdentifier: SearchTeamsCell.identifier
        )
        tableView.dataSource = self
        tableView.delegate = self

        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        tableView.addSubview(refreshControl)
    }

    private func setupMessageState() {


    }

    // MARK: Actions

    @objc func refresh(_ sender: AnyObject) {
        refreshControl.endRefreshing()
    }
}


// MARK: - SearchTeamsPageViewInput

extension SearchTeamsPageViewController: SearchTeamsPageViewInput {

}

// MARK: - UITableViewDelegate

extension SearchTeamsPageViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

// MARK: - UITableViewDataSource

extension SearchTeamsPageViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(
            withIdentifier: SearchTeamsCell.identifier,
            for: indexPath)
        guard let searchCell = cell as? SearchTeamsCell else {
            return cell
        }
        return searchCell
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        1
    }
}
