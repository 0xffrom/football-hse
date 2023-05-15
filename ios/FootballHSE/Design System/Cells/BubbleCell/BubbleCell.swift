//
//  BubbleCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 14.03.2023.
//

import UIKit

class BubbleCell: UICollectionViewCell {

    struct DisplayData {
        let roleName: String
    }

    static let identifier = String(describing: BubbleCell.self)

    @IBOutlet weak var roleLabel: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 4
        clipsToBounds = true
    }

    func configure(content: String) {
        roleLabel.text = content
    }
}
