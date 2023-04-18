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
        DispatchQueue.global().async { [weak self] in
            guard let self else { return }

            let displayModel = response.map { response in
                let positions = PlayerPosition.convertIntToPositions(num: response.playerPosition)
                let tournaments = Tournaments.convertIntToTournaments(num: response.tournaments)

                let displayData = TeamApplicationDisplayModel(
                    id: response.id,
                    teamId: response.teamId,
                    name: response.name,
                    logo: response.logo,
                    contact: response.contact,
                    playerPosition: positions,
                    tournaments: self.convertTournamentsToString(tournaments),
                    description: response.description
                )
                return displayData
            }

            completion(.success(displayModel))
        }
    }

    func convertTournamentsToString(_ tournaments: [Tournaments]) -> String {
        var string = String()

        guard tournaments.count != Tournaments.getMaxCountOfTournaments() else {
            return "На постоянную основу"
        }

        for i in 0..<tournaments.count {
            string.append(tournaments[i].getNameOfTournament())
            if i != tournaments.count - 1 {
                string.append(", ")
            }
        }
        return string
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
