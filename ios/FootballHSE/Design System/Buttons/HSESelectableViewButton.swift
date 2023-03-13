//
//  SelectableViewButton.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 08.03.2023.
//

import UIKit

class HSESelectableViewButton: UIButton {

    // MARK: Public Properties

    var selectedState = false {
        didSet {
            if selectedState {
                setSelectedState()
            } else {
                setNormalState()
            }
        }
    }

    // MARK: Lifecycle

    override func awakeFromNib() {
        super.awakeFromNib()

        setupView()
    }

    // MARK: Private
    
    private func setupView() {
        backgroundColor = UIColor(named: "BaseSurface1")
        tintColor = UIColor(named: "TextAndIconsSecondary")
        layer.cornerRadius = 12
        titleLabel?.font = .systemFont(ofSize: 17)
    }

    private func setNormalState() {
        layer.borderWidth = 0
        layer.borderColor = nil
    }

    private func setSelectedState() {
        layer.borderColor = UIColor(named: "BasePrimary")?.cgColor
        layer.borderWidth = 1
    }
}
