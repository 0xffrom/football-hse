//
//  ProfilePageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class ProfilePageInteractor {
    
    // MARK: Private Data Structures

    private enum Constants {

    }
    
    // MARK: Public Properties
    
    weak var output: ProfilePageInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private

    private func convertResponseToDisplayModel(_ response: [TeamModel], completion: @escaping (Result<TeamModel?, Error>) -> Void) {
        DispatchQueue.global().async {
            let team = response.first { team in
                team.captainPhoneNumber == CurrentUserConfig.shared.phoneNumber
            }

            completion(.success(team))
        }
    }
}

// MARK: - ProfilePageInteractorInput

extension ProfilePageInteractor: ProfilePageInteractorInput {

    func getTeamInfo(completion: @escaping (Result<TeamModel?, Error>) -> Void) {
        let config = RequestConfigWithParser(request: TeamsTarget(), parser: TeamsParser())

        networkService.sendGetRequest(config: config) { result in
            switch result {
            case let .success(data):
                self.convertResponseToDisplayModel(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    func deleteTeam(with id: Int) {
        let config = RequestConfig(request: DeleteTeamsTarget(id: id))

        networkService.sendDeleteRequest(config: config, competionHandler: { result in
            switch result {
            case .success(_):
                print("Заявка удалена")
            case .failure(_):
                print("Ошибка")
            }
        })
    }
}
