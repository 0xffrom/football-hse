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
    @IBOutlet weak var tournaments: UILabel!
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

        teamImage.layer.cornerRadius = teamImage.layer.bounds.width / 2
        teamImage.clipsToBounds = true
        teamImage.layer.borderColor = UIColor(named: "TextIconsTertiary")?.cgColor
        teamImage.layer.borderWidth = 1
        teamImage.contentMode = .scaleAspectFit

        rolesCollectionView.register(
            UINib(nibName: String(describing: BubbleCell.self), bundle: nil),
            forCellWithReuseIdentifier: BubbleCell.identifier
        )
        rolesCollectionView.dataSource = self
        rolesCollectionView.delegate = self
        rolesCollectionView.showsHorizontalScrollIndicator = false
    }

    override func prepareForReuse() {
        teamImage.image = nil
    }

    func configure(teamName: String, tournaments: String?, image: UIImage?, roles: [PlayerPosition]) {
        self.teamName.text = teamName
        self.tournaments.text = tournaments
        self.roles = roles
        self.teamImage.image = image ?? R.image.teamIcon()!
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
            withReuseIdentifier: BubbleCell.identifier,
            for: indexPath)
        let role = roles[indexPath.row]
        guard let searchCell = cell as? BubbleCell else {
            return cell
        }
        searchCell.configure(content: role.getStringValue())
        return searchCell
    }
}
