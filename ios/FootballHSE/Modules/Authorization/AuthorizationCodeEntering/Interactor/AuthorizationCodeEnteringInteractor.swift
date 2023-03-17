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
    private let currentUserConfig: CurrentUserConfig

    // MARK: Lifecycle

    init(
        networkService: INetworkService,
        currentUserConfig: CurrentUserConfig
    ) {
        self.networkService = networkService
        self.currentUserConfig = currentUserConfig
    }
    
    // MARK: Public

    func sendCode(code: String, completion: @escaping (Result<TokensModel, Error>) -> Void) {
        guard let phoneNumber = currentUserConfig.phoneNumber else { return }
        let config = RequestConfig(request: AuthorizationCodeEnteringTarget(phoneNumber: phoneNumber, code: code))

        networkService.sendPostRequest(config: config, competionHandler: { result in
            switch result {
            case let .success(data):
                guard let info = self.parse(data: data) else {
                    completion(.failure(NetworkError.parsingError))
                    return
                }
                completion(.success(info))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }

    func parse(data: Data?) -> TokensModel? {
        guard let data = data,
              let model = try? JSONDecoder().decode(TokensModel.self, from: data)
        else {
            return nil
        }
        return model
    }
}

// MARK: - AuthorizationCodeEnteringInteractorInput

extension AuthorizationCodeEnteringInteractor: AuthorizationCodeEnteringInteractorInput {}
