//
//  TeamApplicationInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class TeamApplicationInteractor {

    // MARK: Public Properties
    
    weak var output: TeamApplicationInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private
}

// MARK: - TeamApplicationInteractorInput

extension TeamApplicationInteractor: TeamApplicationInteractorInput {

}
