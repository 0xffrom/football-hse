//
//  MyApplicationsPageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class MyApplicationsPageInteractor {
    
    // MARK: Private Data Structures

    private enum Constants {

    }
    
    
    // MARK: Public Properties
    
    weak var output: MyApplicationsPageInteractorOutput?
    
    
    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Public
    
    
    // MARK: Private

    private func convertResponseToDisplayModel(_ response: [PlayerApplicationResponseModel], completion: @escaping (Result<[ProfilePlayerApplicationDisplayModel], Error>) -> Void) {
        DispatchQueue.global().async { [weak self] in
            guard let self else { return }

            var displayModel: [ProfilePlayerApplicationDisplayModel] = []
            response.forEach { item in
                guard item.playerPhoneNumber == CurrentUserConfig.shared.phoneNumber else { return }

                let positions = PlayerPosition.convertIntToPositions(num: item.footballPosition)
                let tournaments = Tournament.convertIntToTournaments(num: item.preferredTournaments)

                let model = ProfilePlayerApplicationDisplayModel(
                    playerPosition: positions,
                    tournaments: self.convertTournamentsToString(tournaments)
                )

                displayModel.append(model)
            }

            completion(.success(displayModel))
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

// MARK: - MyApplicationsPageInteractorInput

extension MyApplicationsPageInteractor: MyApplicationsPageInteractorInput {

    func getApplications(completion: @escaping (Result<[ProfilePlayerApplicationDisplayModel], Error>) -> Void) {
        let config = RequestConfigWithParser(request: SearchPlayersTarget(), parser: PlayerApplicationsParser())

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
