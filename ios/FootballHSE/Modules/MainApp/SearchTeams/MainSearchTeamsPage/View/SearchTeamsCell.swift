//
//  SearchTeamsCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 14.03.2023.
//

import UIKit

class SearchTeamsCell: UITableViewCell {

    static let identifier = String(describing: SearchTeamsCell.self)

    @IBOutlet weak var rolesCollectionView: UICollectionView!
    @IBOutlet weak var applicationDescription: UILabel!
    @IBOutlet weak var moreButton: UIButton!
    @IBOutlet weak var teamName: UILabel!
    @IBOutlet weak var teamImage: UIImageView!

    private var roles: [PlayerPosition] = [] {
        didSet {
            rolesCollectionView.reloadData()
        }
    }

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
        teamImage.image = UIImage(named: "defaultTeamImage")

        rolesCollectionView.register(
            UINib(nibName: String(describing: RoleCell.self), bundle: nil),
            forCellWithReuseIdentifier: RoleCell.identifier
        )
        rolesCollectionView.dataSource = self
        rolesCollectionView.delegate = self
        rolesCollectionView.showsHorizontalScrollIndicator = false
    }

    override func prepareForReuse() {
        teamImage.image = UIImage(named: "defaultTeamImage")
    }

    func configure(teamName: String, applicationDescription: String, image: String?, roles: [PlayerPosition]) {
        self.teamName.text = teamName
        self.applicationDescription.text = applicationDescription
        self.roles = roles
//        if let imageURL = image {
//            teamImage.downloaded(from: imageURL)
//        }
    }
}

extension SearchTeamsCell: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        rolesCollectionView.deselectItem(at: indexPath, animated: true)
    }
}

extension SearchTeamsCell: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        roles.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = rolesCollectionView.dequeueReusableCell(
            withReuseIdentifier: RoleCell.identifier,
            for: indexPath)
        let role = roles[indexPath.row]
        guard let searchCell = cell as? RoleCell else {
            return cell
        }
        searchCell.configure(role: role)
        return searchCell
    }
}
