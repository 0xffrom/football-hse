//
//  CreateTeamSearchApplicationInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class CreateTeamSearchApplicationInteractor {

    // MARK: Public Properties
    
    weak var output: CreateTeamSearchApplicationInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private
}

// MARK: - CreateTeamSearchApplicationInteractorInput

extension CreateTeamSearchApplicationInteractor: CreateTeamSearchApplicationInteractorInput {

}
