//
//  ProfilePageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class ProfilePageViewController: UIViewController {
    
    // MARK: Private data structures

    // MARK: Outlets

    @IBOutlet weak var profileWrapperView: UIView!
    @IBOutlet weak var prifileImage: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var editButtton: UIButton!
    @IBOutlet weak var exitButtton: UIButton!
    @IBOutlet weak var registerTeamView: UIView!
    @IBOutlet weak var naApplicationsImageView: UIImageView!
    @IBOutlet weak var noApplicationLabel: UILabel!
    // @IBOutlet weak var applcationsTabelView: UITableView!

    // MARK: Public Properties

    var output: ProfilePageViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        nameLabel.text = CurrentUserConfig.shared.name
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }

    // MARK: Private

    private func setupView() {
        editButtton.setTitle("", for: .normal)
        editButtton.addTarget(self, action: #selector(edit), for: .touchUpInside)
        exitButtton.addTarget(self, action: #selector(exit), for: .touchUpInside)
        let tap = UITapGestureRecognizer(target: self, action: #selector(self.registerTeam(_:)))
        registerTeamView.addGestureRecognizer(tap)
        profileWrapperView.layer.cornerRadius = 16
        prifileImage.layer.cornerRadius = 16
        registerTeamView.layer.cornerRadius = 15
    }

    // MARK: Actions

    @objc private func edit(sender: UIButton) {
        output?.viewWantToEditProfile()
    }

    @objc private func exit(sender: UIButton) {
        output?.exit()
    }

    @objc func registerTeam(_ sender: UITapGestureRecognizer? = nil) {
        output?.registerTeam()
    }
}


// MARK: - ProfilePageViewInput

extension ProfilePageViewController: ProfilePageViewInput {

}
