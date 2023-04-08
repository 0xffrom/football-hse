//
//  TeamApplicationViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class TeamApplicationViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var iamge: UIImageView!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var rolesCollection: UICollectionView!
    @IBOutlet weak var contactLabel: UILabel!
    @IBOutlet weak var infoLabel: UILabel!
    @IBOutlet weak var rolesView: UIView!
    @IBOutlet weak var contactView: UIView!
    @IBOutlet weak var infoView: UIView!

    @IBOutlet weak var saveButton: HSEMainButton!

    // MARK: Public Properties

    var output: TeamApplicationViewOutput?

    var data: TeamApplicationDisplayModel? = nil

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        navigationItem.title = "Заявка";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes

        iamge.layer.cornerRadius = iamge.layer.bounds.width / 2
        iamge.clipsToBounds = true
        iamge.layer.borderColor = UIColor(named: "TextIconsTertiary")?.cgColor
        iamge.layer.borderWidth = 1
        iamge.contentMode = .scaleAspectFit
        iamge.image = UIImage(named: "defaultTeamImage")

        rolesCollection.register(
            UINib(nibName: String(describing: RoleCell.self), bundle: nil),
            forCellWithReuseIdentifier: RoleCell.identifier
        )
        rolesCollection.dataSource = self
        rolesCollection.delegate = self
        rolesCollection.showsHorizontalScrollIndicator = false

        rolesView.layer.cornerRadius = 12
        contactView.layer.cornerRadius = 12
        infoView.layer.cornerRadius = 12

        name.text = data!.name
        contactLabel.text = data!.contact
        infoLabel.text = data!.description
    }
}


extension TeamApplicationViewController: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        rolesCollection.deselectItem(at: indexPath, animated: true)
    }
}

extension TeamApplicationViewController: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        data!.playerPosition.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = rolesCollection.dequeueReusableCell(
            withReuseIdentifier: RoleCell.identifier,
            for: indexPath)
        let role = data!.playerPosition[indexPath.row]
        guard let searchCell = cell as? RoleCell else {
            return cell
        }
        searchCell.configure(role: role)
        return searchCell
    }
}

// MARK: - TeamApplicationViewInput

extension TeamApplicationViewController: TeamApplicationViewInput {

}
