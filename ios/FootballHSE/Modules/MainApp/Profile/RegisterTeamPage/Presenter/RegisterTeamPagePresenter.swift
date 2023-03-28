//
//  RegisterTeamPagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

final class RegisterTeamPagePresenter {

    // MARK: Public Properties

    weak var view: RegisterTeamPageViewInput?
    var moduleOutput: RegisterTeamPageModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: RegisterTeamPageInteractorInput

    // MARK: Lifecycle

    init(interactor: RegisterTeamPageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - RegisterTeamPageModuleInput

extension RegisterTeamPagePresenter: RegisterTeamPageModuleInput {}

// MARK: - RegisterTeamPageInteractorOutput

extension RegisterTeamPagePresenter: RegisterTeamPageInteractorOutput {}

// MARK: - RegisterTeamPageViewOutput

extension RegisterTeamPagePresenter: RegisterTeamPageViewOutput {

    func save() {
        moduleOutput?.back()
    }
}
