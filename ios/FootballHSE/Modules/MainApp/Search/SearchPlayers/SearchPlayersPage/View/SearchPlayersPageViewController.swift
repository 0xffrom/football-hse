//
//  SearchPlayersPageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class SearchPlayersPageViewController: UIViewController {
    
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
    @IBOutlet weak var refreshButton: UIButton!

    @IBOutlet weak var tableView: UITableView!
    private let refreshControl = UIRefreshControl()

    // MARK: Public Properties

    var output: SearchPlayersPageViewOutput?

    // MARK: Private Properties

    var data: [PlayersApplicationDisplayModel] = [] {
        didSet {
            tableView.reloadData()
        }
    }

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        hideKeyboardWhenTappedAround()
        setupView()
        output?.viewDidLoad()
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
        setupSearchBar()
        setupFilters()
        setupTableView()
        setupRefreshButton()
        setupCreateApplicationButton()

        messageLabel.isHidden = true
        messageImageView.isHidden = true
    }

    private func setupSearchBar() {
        searchBar.delegate = self
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

    private func setupFilters() {
        filterButton.setTitle("", for: .normal)
        filterButton.addTarget(self, action: #selector(openFilters), for: .touchUpInside)
    }

    private func setupCreateApplicationButton() {
        createApplicationButton.setTitle("Создать заявку на поиск игрока", for: .normal)
        createApplicationButton.addTarget(self, action: #selector(createApplication), for: .touchUpInside)
    }

    private func setupRefreshButton() {
        refreshButton.addTarget(self, action: #selector(refresh), for: .touchUpInside)
    }

    private func setupTableView() {
        tableView.register(
            UINib(nibName: String(describing: SearchPlayersCell.self), bundle: nil),
            forCellReuseIdentifier: SearchPlayersCell.identifier
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

    @objc private func openFilters(_ sender: AnyObject) {
        output?.viewWantsToOpenFilters()
    }

    @objc private func createApplication(sender: UIButton) {
        output?.viewWantsToOpenCreateApplictaionScreen()
    }
}

// MARK: - SearchPlayersPageViewInput

extension SearchPlayersPageViewController: SearchPlayersPageViewInput {

    func setupLoadingState() {
        if !refreshControl.isRefreshing {
            refreshControl.beginRefreshing()
        }
        tableView.isHidden = false
        messageLabel.isHidden = true
        messageImageView.isHidden = true
        refreshButton.isHidden = true
    }

    func setupDataState(with data: [PlayersApplicationDisplayModel]) {
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

    func showNewApplicationWasCreatedMessage() {
        let alert = UIAlertController(title: "Заявка была успешно создана", message: "Вы можете увидеть ее в Профиле", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }

    func showNewApplicationWasNotCreatedMessage() {
        let alert = UIAlertController(title: "При создании заявки произошла ошибка", message: "Проверьте подключение и повторите попытку", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }

    func nothingFoundState() {
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }

        tableView.isHidden = true
        messageLabel.isHidden = false
        messageImageView.isHidden = false
        refreshButton.isHidden = true

        messageLabel.text = Constants.noResultsString
        messageImageView.image = UIImage(named: "noResultsSearchState")
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
        messageImageView.image = UIImage(named: "emptySearchState")
    }

}

// MARK: - UITableViewDelegate

extension SearchPlayersPageViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        output?.wantsToOpenPlayersApplication(player: data[indexPath.section])
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

extension SearchPlayersPageViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(
            withIdentifier: SearchPlayersCell.identifier,
            for: indexPath)
        guard let searchCell = cell as? SearchPlayersCell else {
            return cell
        }
        let application = data[indexPath.section]
        searchCell.configure(
            playerName: application.name,
            tournaments: application.tournaments,
            image: application.photo,
            roles: application.footballPosition
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

extension SearchPlayersPageViewController: UISearchBarDelegate {

    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        output?.searchBarTextDidChange(text: searchText)
    }

    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        self.searchBar.endEditing(true)
    }
}
