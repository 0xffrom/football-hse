//
//  SearchTeamsPageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class SearchTeamsPageInteractor {

    // MARK: Public Properties
    
    weak var output: SearchTeamsPageInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private

    private func convertResponseToDisplayModel(_ response: [TeamApplicationResponseModel], completion: @escaping (Result<[TeamApplicationDisplayModel], Error>) -> Void) {

        

        DispatchQueue.global().async {
            let displayModel = response.map { response in
                let positions = PlayerPosition.convertIntToPositions(num: response.playerPosition)
                let displayData = TeamApplicationDisplayModel(
                    id: response.id,
                    teamId: response.teamId,
                    name: response.name,
                    logo: response.logo,
                    contact: response.contact,
                    playerPosition: positions,
                    tournaments: response.tournaments,
                    description: response.description
                )
                return displayData
            }

            completion(.success(displayModel))
        }
    }
}

// MARK: - SearchTeamsPageInteractorInput

extension SearchTeamsPageInteractor: SearchTeamsPageInteractorInput {

    // MARK: Public

    func getSearchResults(completion: @escaping (Result<[TeamApplicationDisplayModel], Error>) -> Void) {
        let config = RequestConfigWithParser(request: SearchTeamsTarget(), parser: TeamApplicationsParser())

        networkService.sendGetRequest(config: config) { result in
            switch result {
            case let .success(data):
                self.convertResponseToDisplayModel(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }
}
