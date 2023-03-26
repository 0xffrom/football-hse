//
//  RoleCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 14.03.2023.
//

import UIKit

class RoleCell: UICollectionViewCell {

    struct DisplayData {
        let roleName: String
    }

    static let identifier = String(describing: RoleCell.self)

    @IBOutlet weak var roleLabel: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 4
        clipsToBounds = true
    }

    func configure(role: PlayerPosition) {
        let roleString = role.getNameOfRole()
        roleLabel.text = roleString
    }
}
