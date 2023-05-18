//
//  RegistrationInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class RegistrationInteractor {

    // MARK: Public Properties
    
    weak var output: RegistrationInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init( networkService: INetworkService) {
        self.networkService = networkService
    }

    private func createConversation(_ data: Data?, _ completion: @escaping (Result<Data?, Error>) -> Void) {
        networkService.sendPostRequestWithParser(
            config: RequestConfigWithParser(
                request: CreateConversationTarget(
                    phoneNumber: "88888888888",
                    name: nil,
                    image: nil
                ),
                parser: ConversationParser()
            )) { result in
                switch result {
                case .success(_):
                    completion(.success(data))
                case let .failure(error):
                    completion(.failure(error))
                }
            }
    }
}

// MARK: - RegistrationInteractorInput

extension RegistrationInteractor: RegistrationInteractorInput {

    // MARK: Public

    func registerUser(name: String, roleId: Int, completion: @escaping (Result<Data?, Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }
        let target = RegistrationTarget(phoneNumber: phoneNumber, name: name, roleId: roleId)
        let config = RequestConfig(request: target)

        networkService.sendPostRequest(config: config, competionHandler: { [weak self] result in
            switch result {
            case let .success(data):
                CurrentUserConfig.shared.name = name
                CurrentUserConfig.shared.hseRole = roleId
                self?.createConversation(data, completion)
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }
}
