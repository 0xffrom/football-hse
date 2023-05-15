//
//  RegisterTeamView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import UIKit

final class RegisterTeamView: UIView {

    private var action: (() -> Void)?

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 15
        clipsToBounds = true
    }

    func configureAction(_ action: (() -> Void)?) {
        self.action = action
        let registerTeamTap = UITapGestureRecognizer(target: self, action: #selector(self.registerTeam(_:)))
        addGestureRecognizer(registerTeamTap)
    }

    @objc func registerTeam(_ sender: UITapGestureRecognizer? = nil) {
        action?()
    }
}
