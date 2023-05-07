//
//  ProfilePagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class ProfilePagePresenter {

    // MARK: Public Properties

    weak var view: ProfilePageViewInput?
    var moduleOutput: ProfilePageModuleOutput?
    
    // MARK: Private Properties

    private let interactor: ProfilePageInteractorInput
    private var teamId: Int?

    // MARK: Lifecycle

    init(interactor: ProfilePageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private

    private func handleTeamRequest(_ result: Result<TeamModel?, Error>) {
        switch result {
        case .success(let team):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                if team == nil {
                    view.setupRegisterTeamView()
                } else if team?.status == 0 {
                    view.setupTeamRegistrationInProgressView()
                } else if team?.status == 1 {
                    view.setupTeamIsRegisteredView(nameOfTeam: team?.name)
                } else if team?.status == 2 {
                    view.setupRegisterTeamView()
                    view.showTeamDeclineAlert()
                    self.teamId = team?.id
                }
            }
        case .failure(_):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                view.setupErrorWhileLoadingTeamView()
            }
        }
    }

    private func getTeamInfo() {
        interactor.getTeamInfo { [weak self] result in
            self?.handleTeamRequest(result)
        }
    }
}

// MARK: - ProfilePageModuleInput

extension ProfilePagePresenter: ProfilePageModuleInput {

    func updateProfileInfo() {
        view?.updateInfo()
    }

    func updateTeamInfo() {
        view?.setupTeamRegistrationInProgressView()
    }
}

// MARK: - ProfilePageInteractorOutput

extension ProfilePagePresenter: ProfilePageInteractorOutput {}

// MARK: - ProfilePageViewOutput

extension ProfilePagePresenter: ProfilePageViewOutput {

    func viewDidLoad() {
        moduleOutput?.moduleDidLoad(self)
        view?.setupLoadingState()
        getTeamInfo()
    }

    func viewWantToRefreshTeamInfo() {
        view?.setupLoadingState()
        getTeamInfo()
    }

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

    func deleteDeclinedTeam() {
        guard let teamId else { return }
        interactor.deleteTeam(with: teamId)
    }
}
