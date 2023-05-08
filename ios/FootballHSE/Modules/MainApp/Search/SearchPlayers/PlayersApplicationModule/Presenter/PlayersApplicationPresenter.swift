//
//  PlayersApplicationPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class PlayersApplicationPresenter {

    // MARK: Public Properties

    weak var view: PlayersApplicationViewInput?
    weak var moduleOutput: PlayersApplicationModuleOutput?
    
    // MARK: Private Properties

    private let interactor: PlayersApplicationInteractorInput

    // MARK: Lifecycle

    init(interactor: PlayersApplicationInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - PlayersApplicationModuleInput

extension PlayersApplicationPresenter: PlayersApplicationModuleInput {}

// MARK: - PlayersApplicationInteractorOutput

extension PlayersApplicationPresenter: PlayersApplicationInteractorOutput {}

// MARK: - PlayersApplicationViewOutput

extension PlayersApplicationPresenter: PlayersApplicationViewOutput {}
