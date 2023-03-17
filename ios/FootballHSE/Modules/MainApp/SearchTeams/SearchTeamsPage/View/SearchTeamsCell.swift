//
//  SearchTeamsCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 14.03.2023.
//

import UIKit

class SearchTeamsCell: UITableViewCell {

    struct DisplayData {
        let teamName: String
        let applicationDescription: String
        // let teamImage
    }

    static let identifier = String(describing: SearchTeamsCell.self)

    @IBOutlet weak var rolesCollectionView: UICollectionView!
    @IBOutlet weak var applicationDescription: UILabel!
    @IBOutlet weak var moreButton: UIButton!
    @IBOutlet weak var teamName: UILabel!
    @IBOutlet weak var teamImage: UIImageView!

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 12
        clipsToBounds = true

        moreButton.setTitle("", for: .normal)

        teamImage.layer.cornerRadius = teamImage.layer.bounds.width / 2
        teamImage.clipsToBounds = true
        teamImage.layer.borderColor = UIColor(named: "TextIconsTertiary")?.cgColor
        teamImage.layer.borderWidth = 1
        teamImage.contentMode = .scaleAspectFit

        rolesCollectionView.register(
            UINib(nibName: String(describing: RoleCell.self), bundle: nil),
            forCellWithReuseIdentifier: RoleCell.identifier
        )
        rolesCollectionView.dataSource = self
        rolesCollectionView.delegate = self

        configure()
    }

    func configure() {
        teamImage.image = UIImage(named: "defaultTeamImage")
        teamName.text = "ФК Неизвестные"
        applicationDescription.text = "Вторая лига, зимний турнир"
    }
}

extension SearchTeamsCell: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        rolesCollectionView.deselectItem(at: indexPath, animated: true)
    }
}

extension SearchTeamsCell: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        1
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = rolesCollectionView.dequeueReusableCell(
            withReuseIdentifier: RoleCell.identifier,
            for: indexPath)
        guard let searchCell = cell as? RoleCell else {
            return cell
        }
        return searchCell
    }
}
