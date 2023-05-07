//
//  TeamErrorStateView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import UIKit

final class TeamErrorStateView: UIView {

    private var action: (() -> Void)?

    @IBOutlet weak var refreshButtton: UIButton!

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 15
        clipsToBounds = true
    }

    func configureAction(_ action: (() -> Void)?) {
        self.action = action
        refreshButtton.addTarget(self, action: #selector(updateTeam), for: .touchUpInside)
    }

    @objc func updateTeam(_ sender: UITapGestureRecognizer? = nil) {
        action?()
    }
}
