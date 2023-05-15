//
//  TeamPagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class TeamPagePresenter {

    // MARK: Public Properties

    weak var view: TeamPageViewInput?
    weak var moduleOutput: TeamPageModuleOutput?
    
    // MARK: Private Properties

    private let interactor: TeamPageInteractorInput

    // MARK: Lifecycle

    init(interactor: TeamPageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - TeamPageModuleInput

extension TeamPagePresenter: TeamPageModuleInput {}

// MARK: - TeamPageInteractorOutput

extension TeamPagePresenter: TeamPageInteractorOutput {}

// MARK: - TeamPageViewOutput

extension TeamPagePresenter: TeamPageViewOutput {

    func deleteTeam() {
        view?.setLoadingState()
        interactor.deleteTeam() { [weak self] result in
            guard let self = self, let view = self.view else { return }
            switch result {
            case .success(_):
                DispatchQueue.main.asyncAfter(deadline: .now() + 1) { [weak self] in
                    guard let self = self else { return }
                    view.removeLoadingState()
                    self.moduleOutput?.backWithTeamDeleted()
                }
            case .failure(_):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.removeLoadingState()
                    view.showAlert()
                }
            }
        }
    }

    func openTeamApplications() {
        moduleOutput?.openTeamApplications()
    }
}
