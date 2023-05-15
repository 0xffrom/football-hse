//
//  SearchTeamsPageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation
import UIKit

final class SearchTeamsPageInteractor {

    // MARK: Public Properties
    
    weak var output: SearchTeamsPageInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    private var teamsApplications: [TeamApplicationResponseModel] = []

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private

    private func convertResponseToDisplayModel(_ response: [TeamApplicationResponseModel], completion: @escaping (Result<[TeamApplicationDisplayModel], Error>) -> Void) {
        DispatchQueue.global().async { [weak self] in
            guard let self else { return }

            Task {
                self.teamsApplications = response
                var displayModel: [TeamApplicationDisplayModel] = []

                for responseItem in response {
                    let positions = PlayerPosition.convertIntToPositions(num: responseItem.playerPosition)
                    let tournaments = Tournament.convertIntToTournaments(num: responseItem.tournaments)

                    let image = await self.networkService.downlandImage(url: responseItem.logo)

                    let displayItem = TeamApplicationDisplayModel(
                        id: responseItem.id,
                        teamId: responseItem.teamId,
                        name: responseItem.name,
                        logo: image,
                        contact: responseItem.contact,
                        playerPosition: positions,
                        tournaments: self.convertTournamentsToString(tournaments),
                        description: responseItem.description
                    )

                    displayModel.append(displayItem)
                }

                completion(.success(displayModel))
            }
        }
    }

    func convertTournamentsToString(_ tournaments: [Tournament]) -> String {
        var string = String()

        guard tournaments.count != Tournament.getMaxCountOfTournaments() else {
            return "На постоянную основу"
        }

        for i in 0..<tournaments.count {
            string.append(tournaments[i].getStringValue())
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

    func getSearchResultsWithFilters(position: Int, tournaments: Int,
                                     completion: @escaping (Result<[TeamApplicationDisplayModel], Error>) -> Void) {
        let config = RequestConfigWithParser(
            request: SearchTeamsWithFilterTarget(
                position: position,
                tournaments: tournaments
            ),
            parser: TeamApplicationsParser()
        )

        networkService.sendGetRequest(config: config) { result in
            switch result {
            case let .success(data):
                self.convertResponseToDisplayModel(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    func createNewApplication(position: Int, tournaments: Int, completion: @escaping (Result<Void, Error>) -> Void) {
        let config = RequestConfig(
            request: CreatePlayerApplicationsTarget(
                position: position,
                tournaments: tournaments
            )
        )

        networkService.sendPostRequest(config: config) { result in
            switch result {
            case .success(_):
                completion(.success(Void()))
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    func getImageURLForTeam(with id: Int) -> String? {
        var teamsApplication: TeamApplicationResponseModel?
        teamsApplication = self.teamsApplications.first(where: {
            $0.id == id
        })
        return teamsApplication?.logo
    }
}
