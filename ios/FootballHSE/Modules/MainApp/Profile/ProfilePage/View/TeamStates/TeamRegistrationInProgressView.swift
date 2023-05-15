//
//  TeamRegistrationInProgressView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import UIKit

final class TeamRegistrationInProgressView: UIView {

    private var action: (() -> Void)?

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 15
        clipsToBounds = true
    }

    func configureAction(_ action: (() -> Void)?) {
        self.action = action
        let showTeamTap = UITapGestureRecognizer(target: self, action: #selector(self.showTeam(_:)))
        addGestureRecognizer(showTeamTap)
    }

    @objc func showTeam(_ sender: UITapGestureRecognizer? = nil) {
        action?()
    }
}
