//
//  SearchTeamsPlayerRoleFilterViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class SearchTeamsPlayerRoleFilterViewController: UIViewController {

    struct DisplayData {
        struct FilterCellDisplayData {
            let filterName: String
            let checkAction: ((Int) -> Void)?
        }
        let data: [FilterCellDisplayData]
        let title: String
        let isLast: Bool
    }

    // MARK: Outlets

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var saveButton: HSEMainButton!

    // MARK: Public Properties

    var output: SearchTeamsPlayerRoleFilterViewOutput?

    // MARK: Private Properties
    private var data: [DisplayData.FilterCellDisplayData] = [] {
        didSet {
            tableView.reloadData()
        }
    }

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        output?.viewDidLoad()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        setupTableView()
        setupNavigationBar()
        setupSaveButton()
    }

    private func setupSaveButton() {
        saveButton.addTarget(self, action: #selector(saveButtonTapped), for: .touchUpInside)
    }

    private func setupNavigationBar() {
        navigationItem.title = title

        let image = UIImage(systemName: "chevron.backward")
        image?.withTintColor(R.color.textAndIconsPrimary() ?? .black)
        let backBTN = UIBarButtonItem(image: image,
                                      style: .plain,
                                      target: self,
                                      action: #selector(goBack))
        navigationItem.leftBarButtonItem = backBTN

        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes
    }

    private func setupTableView() {
        tableView.register(
            UINib(nibName: String(describing: FilterCell.self), bundle: nil),
            forCellReuseIdentifier: FilterCell.identifier
        )

        tableView.dataSource = self
        tableView.delegate = self

        tableView.showsVerticalScrollIndicator = false
    }

    // MARK: Actions

    @objc private func saveButtonTapped(_ sender: AnyObject) {
        output?.saveSelections()
    }

    @objc private func goBack(_ sender: AnyObject) {
        output?.removeSelection()
        self.navigationController?.popViewController(animated: true)
    }
}

// MARK: - UITableViewDelegate

extension SearchTeamsPlayerRoleFilterViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }

    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        4
    }

    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        UIView()
    }
}

// MARK: - UITableViewDataSource

extension SearchTeamsPlayerRoleFilterViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(
            withIdentifier: FilterCell.identifier,
            for: indexPath)
        guard let filterCell = cell as? FilterCell else {
            return cell
        }
        let item = data[indexPath.section]
        filterCell.configure(displayData: .init(
            filterName: item.filterName,
            checkAction: {
                item.checkAction?(indexPath.section)
            }
        ))
        return filterCell
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        1
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        data.count
    }
}

// MARK: - SearchTeamsPlayerRoleFilterViewInput

extension SearchTeamsPlayerRoleFilterViewController: SearchTeamsPlayerRoleFilterViewInput {

    func setupDataState(with data: DisplayData) {
        self.data = data.data
        titleLabel.text = data.title
        if data.isLast {
            saveButton.setTitle("Сохранить", for: .normal)
        } else {
            saveButton.setTitle("Продолжить", for: .normal)
        }
    }

    func showAlertMustChooseAllFilters() {
        let alert = UIAlertController(title: "Необходимо выбрать как минимум один пункт", message: nil, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}
