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

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }
}

// MARK: - AuthorizationPhoneEnteringInteractorInput

extension AuthorizationPhoneEnteringInteractor: AuthorizationPhoneEnteringInteractorInput {

    // MARK: Public

    func authorize(with phoneNumber: String, completion: @escaping (Result<Data?, Error>) -> Void) {
        let phoneNumberWithCode = "8" + phoneNumber
        let config = RequestConfig(request: AuthorizationPhoneEnteringTarget(phoneNumber: phoneNumberWithCode))

        networkService.sendPostRequest(config: config, competionHandler: { result in
            switch result {
            case let .success(data):
                CurrentUserConfig.shared.phoneNumber = phoneNumberWithCode
                completion(.success(data))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }
}
