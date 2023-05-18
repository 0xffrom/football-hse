//
//  AuthorizationCodeEnteringInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class AuthorizationCodeEnteringInteractor {

    // MARK: Public Properties
    
    weak var output: AuthorizationCodeEnteringInteractorOutput?
    
    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init( networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private

    private func parse(data: Data?) -> TokensModel? {
        guard let data = data,
              let model = try? JSONDecoder().decode(TokensModel.self, from: data)
        else {
            return nil
        }
        return model
    }
}

// MARK: - AuthorizationCodeEnteringInteractorInput

extension AuthorizationCodeEnteringInteractor: AuthorizationCodeEnteringInteractorInput {

    // MARK: Public

    func sendCode(code: String, completion: @escaping (Result<TokensModel, Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }
        let config = RequestConfig(request: AuthorizationCodeEnteringTarget(phoneNumber: phoneNumber, code: code))

        networkService.sendPostRequest(config: config, competionHandler: { [weak self] result in
            guard let self = self else { return }
            switch result {
            case let .success(data):
                guard let info = self.parse(data: data) else {
                    completion(.failure(NetworkError.parsingError))
                    return
                }
                CurrentUserConfig.shared.isCaptain = info.isCaptain
                CurrentUserConfig.shared.refreshToken = info.refreshToken
                CurrentUserConfig.shared.token = info.token
                completion(.success(info))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }

    func getCurrentUser(completion: @escaping (Result<User, Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }
        let config = RequestConfigWithParser(request: UserTarget(phoneNumber: phoneNumber), parser: UserParser())

        networkService.sendGetRequest(config: config, competionHandler: { [weak self] result in
            switch result {
            case let .success(data):
                CurrentUserConfig.shared.name = data.name
                CurrentUserConfig.shared.phoneNumber = data.phoneNumber
                CurrentUserConfig.shared.isCaptain = data.isCaptain
                CurrentUserConfig.shared.about = data.about
                CurrentUserConfig.shared.contact = data.contact
                CurrentUserConfig.shared.footballExperience = data.footballExperience
                CurrentUserConfig.shared.tournamentExperience = data.tournamentExperience
                CurrentUserConfig.shared.photo = data.photo
                CurrentUserConfig.shared.hseRole = data.hseRole
                CurrentUserConfig.shared.applicationId = data.applicationId
                self?.getTeamInfo(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }

    func getTeamInfo(_ user: User, completion: @escaping (Result<User, Error>) -> Void) {
        let config = RequestConfigWithParser(request: TeamsTarget(), parser: TeamsParser())

        networkService.sendGetRequest(config: config) { result in
            switch result {
            case let .success(data):
                DispatchQueue.global().async {
                    let team = data.first { team in
                        team.captainPhoneNumber == CurrentUserConfig.shared.phoneNumber
                    }

                    if team != nil {
                        CurrentTeamConfig.shared.name = team?.name
                        CurrentTeamConfig.shared.about = team?.about
                        CurrentTeamConfig.shared.photoUrl = team?.logo
                        CurrentTeamConfig.shared.status = team?.status
                        CurrentTeamConfig.shared.id = team?.id
                    }

                    completion(.success(user))
                }
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }
}
