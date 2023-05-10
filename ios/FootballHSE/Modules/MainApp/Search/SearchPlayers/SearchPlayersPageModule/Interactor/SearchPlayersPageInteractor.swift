//
//  SearchPlayersPageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation
import UIKit

final class SearchPlayersPageInteractor {

    // MARK: Public Properties
    
    weak var output: SearchPlayersPageInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private

    private func convertResponseToDisplayModel(_ response: [PlayerApplicationResponseModel], completion: @escaping (Result<[PlayersApplicationDisplayModel], Error>) -> Void) {
        DispatchQueue.global().async { [weak self] in
            guard let self else { return }

            Task {
                var displayModel: [PlayersApplicationDisplayModel] = []

                for responseItem in response {
                    let positions = PlayerPosition.convertIntToPositions(num: responseItem.footballPosition)
                    let tournaments = Tournament.convertIntToTournaments(num: responseItem.preferredTournaments)

                    let image = await self.networkService.downlandImage(url: responseItem.photo)

                    let displayItem = PlayersApplicationDisplayModel(
                        id: responseItem.id,
                        playerPhoneNumber: responseItem.playerPhoneNumber,
                        name: responseItem.name,
                        photo: image,
                        contact: responseItem.contact,
                        footballPosition: positions,
                        tournaments: self.convertTournamentsToString(tournaments),
                        footballExperience: responseItem.footballExperience,
                        tournamentExperience: responseItem.tournamentExperience
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

// MARK: - SearchPlayersPageInteractorInput

extension SearchPlayersPageInteractor: SearchPlayersPageInteractorInput {

    // MARK: Public

    func getSearchResults(completion: @escaping (Result<[PlayersApplicationDisplayModel], Error>) -> Void) {
        let config = RequestConfigWithParser(request: SearchPlayersTarget(), parser: PlayerApplicationsParser())

        networkService.sendGetRequest(config: config) { [weak self] result in
            guard let self else { return }
            switch result {
            case let .success(data):
                self.convertResponseToDisplayModel(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    func getSearchResultsWithFilters(position: Int, tournaments: Int,
                                     completion: @escaping (Result<[PlayersApplicationDisplayModel], Error>) -> Void) {
        let config = RequestConfigWithParser(
            request: SearchPlayersWithFilterTarget(
                position: position,
                tournaments: tournaments
            ),
            parser: PlayerApplicationsParser()
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

    func createNewApplication(position: Int, tournaments: Int, completion: @escaping (Result<Data?, Error>) -> Void) {
        let config = RequestConfig(
            request: CreateTeamApplicationsTarget(
                position: position,
                tournaments: tournaments
            )
        )

        networkService.sendPostRequest(config: config) { result in
            switch result {
            case .success(_):
                completion(.success(nil))
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }
}
