//
//  HSEButton.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.03.2023.
//

import UIKit

class HSEMainButton: UIButton {

    // MARK: Lifecycle

    override func awakeFromNib() {
        super.awakeFromNib()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        backgroundColor = UIColor(named: "BasePrimary")
        tintColor = UIColor(named: "TextIconsWhite")
        layer.cornerRadius = 14
        titleLabel?.font = .systemFont(ofSize: 18, weight: .semibold)
    }
}
