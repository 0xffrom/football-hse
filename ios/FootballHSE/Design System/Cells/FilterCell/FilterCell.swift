//
//  FilterCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.04.2023.
//

import UIKit

class FilterCell: UITableViewCell {

    struct DisplayData {
        let filterName: String
        let checkAction: (() -> Void)?
    }

    static let identifier = String(describing: FilterCell.self)

    @IBOutlet weak var wrapperView: UIView!
    @IBOutlet weak var filterLabel: UILabel!
    @IBOutlet weak var checkImage: UIImageView!

    private var checkAction: (() -> Void)?
    private var checked: Bool = false

    override func awakeFromNib() {
        super.awakeFromNib()

        wrapperView.layer.cornerRadius = 12
        wrapperView.clipsToBounds = true

        let recognizer = UITapGestureRecognizer(target: self, action: #selector(checkStateChanged))
        checkImage.addGestureRecognizer(recognizer)
    }

    func configure(displayData: DisplayData) {
        filterLabel.text = displayData.filterName
        checkAction = displayData.checkAction
    }

    @objc private func checkStateChanged(sender: UITapGestureRecognizer) {
        if checked {
            checkImage.image = R.image.checkboxUnchecked()
        } else {
            checkImage.image = R.image.checkboxChecked()
        }
        checkAction?()
        checked = !checked
    }
}
