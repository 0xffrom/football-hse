//
//  SearchTeamsPlayerRoleFilterInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class SearchTeamsPlayerRoleFilterInteractor {

    // MARK: Public Properties
    
    weak var output: SearchTeamsPlayerRoleFilterInteractorOutput?
    
    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init( networkService: INetworkService) {
        self.networkService = networkService
    }
}

// MARK: - SearchTeamsPlayerRoleFilterInteractorInput

extension SearchTeamsPlayerRoleFilterInteractor: SearchTeamsPlayerRoleFilterInteractorInput {

}
