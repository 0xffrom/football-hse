//
//  AuthorizationPhoneEnteringInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class AuthorizationPhoneEnteringInteractor {

    // MARK: Public Properties
    
    weak var output: AuthorizationPhoneEnteringInteractorOutput?
    
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

    func authorize(with phoneNumber: String, completion: @escaping (Result<Data?, Error>) -> Void) {
        let phoneNumberWithCode = "8" + phoneNumber
        let config = RequestConfig(request: AuthorizationPhoneEnteringTarget(phoneNumber: phoneNumberWithCode))

        networkService.sendPostRequest(config: config, competionHandler: { [weak self] result in
            guard let self = self else { return }

            switch result {
            case let .success(data):
                self.currentUserConfig.phoneNumber = phoneNumberWithCode
                completion(.success(data))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }
}

// MARK: - AuthorizationPhoneEnteringInteractorInput

extension AuthorizationPhoneEnteringInteractor: AuthorizationPhoneEnteringInteractorInput {}
