//
//  CreateTeamSearchApplicationPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class CreateTeamSearchApplicationPresenter {

    // MARK: Public Properties

    weak var view: CreateTeamSearchApplicationViewInput?
    weak var moduleOutput: CreateTeamSearchApplicationModuleOutput?
    
    // MARK: Private Properties

    private let interactor: CreateTeamSearchApplicationInteractorInput

    // MARK: Lifecycle

    init(interactor: CreateTeamSearchApplicationInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - CreateTeamSearchApplicationModuleInput

extension CreateTeamSearchApplicationPresenter: CreateTeamSearchApplicationModuleInput {}

// MARK: - CreateTeamSearchApplicationInteractorOutput

extension CreateTeamSearchApplicationPresenter: CreateTeamSearchApplicationInteractorOutput {}

// MARK: - CreateTeamSearchApplicationViewOutput

extension CreateTeamSearchApplicationPresenter: CreateTeamSearchApplicationViewOutput {

}
