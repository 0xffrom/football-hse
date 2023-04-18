//
//  SearchTeamsPlayerRoleFilterViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class SearchTeamsPlayerRoleFilterViewController: UIViewController {
    
    // MARK: Private data structures

    // MARK: Outlets

    @IBOutlet weak var tableView: UITableView!

    // MARK: Public Properties

    var output: SearchTeamsPlayerRoleFilterViewOutput?

    // MARK: Private Properties

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        setupTableView()
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
        // let application = data[indexPath.section]
        filterCell.configure(
            displayData: .init(
                filterName: "Центральный атакующий полузащитник",
                checkAction: nil
            )
        )
        return filterCell
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        1
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        10
    }
}

// MARK: - SearchTeamsPlayerRoleFilterViewInput

extension SearchTeamsPlayerRoleFilterViewController: SearchTeamsPlayerRoleFilterViewInput {

}
