//
//  InfoView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 29.04.2023.
//

import UIKit

final class InfoView: UIView {

    // MARK: Outlets

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var infoLabel: UILabel!

    // MARK: Public

    func configure(title: String, info: String) {
        titleLabel.text = title
        infoLabel.text = info
    }
}
