//
//  TeamPageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class TeamPageInteractor {

    // MARK: Public Properties
    
    weak var output: TeamPageInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private
}

// MARK: - TeamPageInteractorInput

extension TeamPageInteractor: TeamPageInteractorInput {

    func deleteTeam(completion: @escaping (Result<Data?, Error>) -> Void) {
        guard let id = CurrentTeamConfig.shared.id else {
            completion(.failure(CommonError()))
            return
        }

        let config = RequestConfig(request: DeleteTeamsTarget(id: id))

        networkService.sendDeleteRequest(config: config, competionHandler: { result in
            switch result {
            case .success(_):
                CurrentTeamConfig.clear()
                CurrentUserConfig.shared.isCaptain = false
                completion(.success(nil))
            case .failure(let error):
                completion(.failure(error))
            }
        })
    }
}
