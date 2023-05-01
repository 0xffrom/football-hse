//
//  ProfilePagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

final class ProfilePagePresenter {

    // MARK: Public Properties

    weak var view: ProfilePageViewInput?
    var moduleOutput: ProfilePageModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: ProfilePageInteractorInput

    // MARK: Lifecycle

    init(interactor: ProfilePageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - ProfilePageModuleInput

extension ProfilePagePresenter: ProfilePageModuleInput {}

// MARK: - ProfilePageInteractorOutput

extension ProfilePagePresenter: ProfilePageInteractorOutput {}

// MARK: - ProfilePageViewOutput

extension ProfilePagePresenter: ProfilePageViewOutput {
    func openMyApplications() {
        moduleOutput?.openMyApplications()
    }

    func registerTeam() {
        moduleOutput?.registerTeam()
    }

    func viewWantToEditProfile() {
        moduleOutput?.openEditProfile()
    }

    func exit() {
        moduleOutput?.exit()
    }
}
