//
//  PlayersApplicationInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class PlayersApplicationInteractor {

    // MARK: Public Properties
    
    weak var output: PlayersApplicationInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService
    private let phoneNumber: String?
    private let name: String?
    private let image: String?

    // MARK: Lifecycle

    init(networkService: INetworkService,
         phoneNumber: String?,
         name: String?,
         image: String?
    ) {
        self.networkService = networkService
        self.phoneNumber = phoneNumber
        self.name = name
        self.image = image
    }

    // MARK: Private

    private func createConversation(_ completion: @escaping (Result<ConversationResponseMosel, Error>) -> Void) {
        networkService.sendPostRequestWithParser(
            config: RequestConfigWithParser(
                request: CreateConversationTarget(
                    phoneNumber: phoneNumber ?? "",
                    name: name,
                    image: image
                ),
                parser: ConversationParser()
            )) { result in
                switch result {
                case let .success(data):
                    completion(.success(data))
                case let .failure(error):
                    completion(.failure(error))
                }
            }
    }
}

// MARK: - PlayersApplicationInteractorInput

extension PlayersApplicationInteractor: PlayersApplicationInteractorInput {
    
    func getChat(completion: @escaping (Result<ConversationResponseMosel, Error>) -> Void) {
        guard let currentUserPhoneNumber = CurrentUserConfig.shared.phoneNumber else { return }

        let config = RequestConfigWithParser(
            request: GetConversationsTarget(phoneNumber: currentUserPhoneNumber),
            parser: ConversationsParser()
        )

        networkService.sendGetRequest(config: config) {  [weak self] result in
            guard let self else { return }
            switch result {
            case let .success(data):
                if let conversation = data.first(where: {
                    $0.phoneNumber1 == self.phoneNumber || $0.phoneNumber2 == self.phoneNumber
                }) {
                    completion(.success(conversation))
                } else {
                    self.createConversation(completion)
                }
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }
}
