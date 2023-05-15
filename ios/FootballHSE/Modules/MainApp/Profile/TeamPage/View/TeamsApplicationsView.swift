//
//  TeamsApplicationsView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import UIKit

final class TeamsApplicationsView: UIView {

    private var action: (() -> Void)?

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 15
        clipsToBounds = true
    }

    func configureAction(_ action: (() -> Void)?) {
        self.action = action
        let tap = UITapGestureRecognizer(target: self, action: #selector(self.tapAction(_:)))
        addGestureRecognizer(tap)
    }

    @objc func tapAction(_ sender: UITapGestureRecognizer? = nil) {
        action?()
    }
}
