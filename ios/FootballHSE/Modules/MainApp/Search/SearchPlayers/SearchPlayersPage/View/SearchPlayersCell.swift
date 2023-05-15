//
//  SearchPlayersCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 14.03.2023.
//

import UIKit

class SearchPlayersCell: UITableViewCell {

    static let identifier = String(describing: SearchPlayersCell.self)

    @IBOutlet weak var rolesCollectionView: UICollectionView!
    @IBOutlet weak var tournaments: UILabel!
    @IBOutlet weak var playerName: UILabel!
    @IBOutlet weak var playerImage: UIImageView!

    private var roles: [PlayerPosition] = [] {
        didSet {
            rolesCollectionView.reloadData()
        }
    }

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 12
        clipsToBounds = true

        playerImage.layer.cornerRadius = playerImage.layer.bounds.width / 2
        playerImage.clipsToBounds = true
        playerImage.layer.borderColor = UIColor(named: "TextIconsTertiary")?.cgColor
        playerImage.layer.borderWidth = 1
        playerImage.contentMode = .scaleAspectFit

        rolesCollectionView.register(
            UINib(nibName: String(describing: BubbleCell.self), bundle: nil),
            forCellWithReuseIdentifier: BubbleCell.identifier
        )
        rolesCollectionView.dataSource = self
        rolesCollectionView.delegate = self
        rolesCollectionView.showsHorizontalScrollIndicator = false
    }

    override func prepareForReuse() {
        playerImage.image = nil
    }

    func configure(playerName: String, tournaments: String?, image: UIImage?, roles: [PlayerPosition]) {
        self.playerName.text = playerName
        self.tournaments.text = tournaments
        self.roles = roles
        self.playerImage.image = image ?? R.image.userIcon()!
    }
}

extension SearchPlayersCell: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        rolesCollectionView.deselectItem(at: indexPath, animated: true)
    }
}

extension SearchPlayersCell: UICollectionViewDataSource {

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
