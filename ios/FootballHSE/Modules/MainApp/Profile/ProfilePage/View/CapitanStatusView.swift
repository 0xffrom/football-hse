//
//  CapitanStatusView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 29.04.2023.
//

import UIKit

final class CapitanStatusView: UIView {

    // MARK: Private data structures

    private enum Constants {
        static let cornerRadius: CGFloat = 4
    }

    // MARK: Outlets

    @IBOutlet weak var statusBubble: UIView!

    // MARK: Lifecycle

    override func awakeFromNib() {
        super.awakeFromNib()

        statusBubble.layer.cornerRadius = Constants.cornerRadius
    }
}
