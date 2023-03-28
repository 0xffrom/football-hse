//
//  TeamApplicationPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class TeamApplicationPresenter {

    // MARK: Public Properties

    weak var view: TeamApplicationViewInput?
    weak var moduleOutput: TeamApplicationModuleOutput?
    
    // MARK: Private Properties

    private let interactor: TeamApplicationInteractorInput

    // MARK: Lifecycle

    init(interactor: TeamApplicationInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - TeamApplicationModuleInput

extension TeamApplicationPresenter: TeamApplicationModuleInput {}

// MARK: - TeamApplicationInteractorOutput

extension TeamApplicationPresenter: TeamApplicationInteractorOutput {}

// MARK: - TeamApplicationViewOutput

extension TeamApplicationPresenter: TeamApplicationViewOutput {}
