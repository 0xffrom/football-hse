//
//  MyApplicationsPageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class MyApplicationsPageInteractor {
    
    // MARK: Public Properties
    
    weak var output: MyApplicationsPageInteractorOutput?
    
    // MARK: Private Properties

    private let networkService: INetworkService
    private let type: TypeOfApplications

    // MARK: Lifecycle

    init(networkService: INetworkService,
         type: TypeOfApplications) {
        self.networkService = networkService
        self.type = type
    }
    
    // MARK: Private

    private func getApplicationsForUser(_ completion: @escaping (Result<[ProfileApplicationDisplayModel], Error>) -> Void) {
        let config = RequestConfigWithParser(request: SearchPlayersTarget(), parser: PlayerApplicationsParser())

        networkService.sendGetRequest(config: config) { result in
            switch result {
            case let .success(data):
                self.prepareUserApplicationsForDisplay(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    private func getApplicationsForTeam(_ completion: @escaping (Result<[ProfileApplicationDisplayModel], Error>) -> Void) {
        let config = RequestConfigWithParser(request: SearchTeamsTarget(), parser: TeamApplicationsParser())

        networkService.sendGetRequest(config: config) { result in
            switch result {
            case let .success(data):
                self.prepareTeamApplicationsForDisplay(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    private func prepareUserApplicationsForDisplay(_ response: [PlayerApplicationResponseModel], completion: @escaping (Result<[ProfileApplicationDisplayModel], Error>) -> Void) {
        DispatchQueue.global().async { [weak self] in
            guard let self else { return }

            var displayModel: [ProfileApplicationDisplayModel] = []
            response.forEach { item in
                guard item.playerPhoneNumber == CurrentUserConfig.shared.phoneNumber else { return }

                let positions = PlayerPosition.convertIntToPositions(num: item.footballPosition)
                let tournaments = Tournament.convertIntToTournaments(num: item.preferredTournaments)

                let model = ProfileApplicationDisplayModel(
                    playerPosition: positions,
                    tournaments: self.convertTournamentsToString(tournaments),
                    id: item.id
                )

                displayModel.append(model)
            }

            completion(.success(displayModel))
        }
    }

    private func prepareTeamApplicationsForDisplay(_ response: [TeamApplicationResponseModel], completion: @escaping (Result<[ProfileApplicationDisplayModel], Error>) -> Void) {
        DispatchQueue.global().async { [weak self] in
            guard let self else { return }

            var displayModel: [ProfileApplicationDisplayModel] = []
            response.forEach { item in
                guard item.contact == CurrentUserConfig.shared.phoneNumber else { return }

                let positions = PlayerPosition.convertIntToPositions(num: item.playerPosition)
                let tournaments = Tournament.convertIntToTournaments(num: item.tournaments)

                let model = ProfileApplicationDisplayModel(
                    playerPosition: positions,
                    tournaments: self.convertTournamentsToString(tournaments),
                    id: item.id
                )

                displayModel.append(model)
            }

            completion(.success(displayModel))
        }
    }

    private func convertTournamentsToString(_ tournaments: [Tournament]) -> String {
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

    private func deleteTeamApplication(with id: Int, _ completion: @escaping (Result<Data?, Error>) -> Void) {
        let config = RequestConfig(request: DeleteTeamApplicationTarget(id: id))

        networkService.sendDeleteRequest(config: config, competionHandler: { result in
            switch result {
            case .success(_):
                completion(.success(nil))
            case .failure(let error):
                completion(.failure(error))
            }
        })
    }

    private func deleteUserApplication(with id: Int, _ completion: @escaping (Result<Data?, Error>) -> Void) {
        let config = RequestConfig(request: DeletePlayerApplicationTarget(id: id))

        networkService.sendDeleteRequest(config: config, competionHandler: { result in
            switch result {
            case .success(_):
                completion(.success(nil))
            case .failure(let error):
                completion(.failure(error))
            }
        })
    }
}

// MARK: - MyApplicationsPageInteractorInput

extension MyApplicationsPageInteractor: MyApplicationsPageInteractorInput {

    func getApplications(completion: @escaping (Result<[ProfileApplicationDisplayModel], Error>) -> Void) {
        switch type {
        case .team:
            getApplicationsForTeam(completion)
        case .user:
            getApplicationsForUser(completion)
        }
    }

    func deleteApplication(with id: Int, completion: @escaping (Result<Data?, Error>) -> Void) {
        switch type {
        case .team:
            deleteTeamApplication(with: id, completion)
        case .user:
            deleteUserApplication(with: id, completion)
        }
    }
}
