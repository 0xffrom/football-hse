//
//  WarningView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 29.04.2023.
//

import UIKit

final class WarningView: UIView {

    // MARK: Private data structures

    private enum Constants {
        static let cornerRadius: CGFloat = 11
        static let height = 62
    }

    // MARK: Outlets

    @IBOutlet weak var addInformationButton: UILabel!

    // MARK: Public Properties

    private var addInformationAction: () -> Void = {}

    // MARK: Lifecycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = Constants.cornerRadius
        clipsToBounds = true
        snp.makeConstraints { make in
            make.height.equalTo(Constants.height)
        }
    }

    // MARK: Public

    func configure(addInformationAction: @escaping () -> Void) {
        self.addInformationAction = addInformationAction
        let tap = UITapGestureRecognizer(target: self, action: #selector(addInformation))
        addInformationButton.isUserInteractionEnabled = true
        addInformationButton.addGestureRecognizer(tap)
    }

    // MARK: Actions

    @objc private func addInformation(_ sender: UITapGestureRecognizer? = nil) {
        addInformationAction()
    }
}
