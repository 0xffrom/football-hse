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

    private enum Constants {
        static let wrapperViewsCornerRadius: CGFloat = 16
        static let profileImageCornerRadius: CGFloat = 16
    }

    // MARK: Outlets

    @IBOutlet weak var profileWrapperView: UIView!
    @IBOutlet weak var profileImage: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var editButtton: UIButton!

    @IBOutlet weak var exitButtton: UIButton!

    @IBOutlet weak var registerTeamView: UIView!
    @IBOutlet weak var myApplicationsView: UIView!

    @IBOutlet weak var profileInfoStackView: UIStackView!

    // MARK: Public Properties

    var output: ProfilePageViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }

    // MARK: Private

    private func setupView() {
        setupExitButton()
        setupProfileView()
        setupMyApplicationsView()
        setupRegisterTeamView()
    }

    private func setupExitButton() {
        exitButtton.addTarget(self, action: #selector(exit), for: .touchUpInside)
    }

    private func setupProfileView() {
        setupProfileWrapperView()
        setupEditButtton()
        setupProfileImage()
        setupNameLabel()
        setupCapitanStatus()
        setupWarningView()
        setupFootballExperience()
        setupTournamentExperience()
        setupContactInfo()
        setupAboutInfo()
    }

    private func setupProfileWrapperView() {
        profileWrapperView.layer.cornerRadius = Constants.wrapperViewsCornerRadius
        profileInfoStackView.distribution = .fillProportionally
    }

    private func setupEditButtton() {
        editButtton.setTitle("", for: .normal)
        editButtton.addTarget(self, action: #selector(edit), for: .touchUpInside)
    }

    private func setupProfileImage() {
        profileImage.layer.cornerRadius = Constants.profileImageCornerRadius
        guard let photoURL = CurrentUserConfig.shared.photo else { return }
        profileImage.downloaded(from: photoURL)
    }

    private func setupNameLabel() {
        nameLabel.text = CurrentUserConfig.shared.name
    }

    private func setupCapitanStatus() {
        guard (CurrentUserConfig.shared.isCaptain ?? false) else { return }
        let capitanStatusView = R.nib.capitanStatusView(withOwner: self)!

        addNewViewToStack(capitanStatusView)
    }

    private func setupWarningView() {
        guard !CurrentUserConfig.isAllInformationProvided() else { return }

        let warningView = R.nib.warningView(withOwner: self)!
        warningView.configure(addInformationAction: { [weak self] in
            guard let self else { return }
            self.output?.viewWantToEditProfile()
        })

        addNewViewToStack(warningView)
    }

    private func setupFootballExperience() {
        guard let footballExperience = CurrentUserConfig.shared.footballExperience else { return }
        let infoView = R.nib.infoView(withOwner: self)!
        infoView.configure(title: "Футбольный опыт", info: footballExperience)

        addNewViewToStack(infoView)
    }

    private func setupTournamentExperience() {
        guard let tournamentExperience = CurrentUserConfig.shared.tournamentExperience else { return }
        let infoView = R.nib.infoView(withOwner: self)!
        infoView.configure(title: "Опыт в турнирах НИУ ВШЭ", info: tournamentExperience)

        addNewViewToStack(infoView)
    }

    private func setupContactInfo() {
        guard let contactInfo = CurrentUserConfig.shared.contact else { return }
        let infoView = R.nib.infoView(withOwner: self)!
        infoView.configure(title: "Контактная информация", info: contactInfo)

        addNewViewToStack(infoView)
    }

    private func setupAboutInfo() {
        guard let aboutInfo = CurrentUserConfig.shared.about else { return }
        let infoView = R.nib.infoView(withOwner: self)!
        infoView.configure(title: "О себе", info: aboutInfo)

        addNewViewToStack(infoView)
    }

    private func addNewViewToStack(_ view: UIView) {
        profileInfoStackView.addArrangedSubview(view)

        profileInfoStackView.sizeToFit()
        profileInfoStackView.layoutIfNeeded()
    }

    private func setupMyApplicationsView() {
        let myApplicationsTap = UITapGestureRecognizer(target: self, action: #selector(self.openMyApplications(_:)))
        myApplicationsView.addGestureRecognizer(myApplicationsTap)
        myApplicationsView.layer.cornerRadius = Constants.wrapperViewsCornerRadius
    }

    private func setupRegisterTeamView() {
        registerTeamView.layer.cornerRadius = Constants.wrapperViewsCornerRadius
        let registerTeamTap = UITapGestureRecognizer(target: self, action: #selector(self.registerTeam(_:)))
        registerTeamView.addGestureRecognizer(registerTeamTap)
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

    @objc func openMyApplications(_ sender: UITapGestureRecognizer? = nil) {
        output?.openMyApplications()
    }
}


// MARK: - ProfilePageViewInput

extension ProfilePageViewController: ProfilePageViewInput {

}
