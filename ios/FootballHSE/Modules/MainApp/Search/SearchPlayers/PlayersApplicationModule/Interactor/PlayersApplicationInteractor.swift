//
//  PlayersApplicationInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class PlayersApplicationInteractor {

    // MARK: Public Properties
    
    weak var output: PlayersApplicationInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private
}

// MARK: - PlayersApplicationInteractorInput

extension PlayersApplicationInteractor: PlayersApplicationInteractorInput {

}
