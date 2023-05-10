//
//  ConversationInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation
import UIKit

final class ConversationInteractor {

    // MARK: Public Properties
    
    weak var output: ConversationInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService
    private let interlocutorsPhoneNamber: String

    // MARK: Lifecycle

    init(
        networkService: INetworkService,
        interlocutorsPhoneNamber: String
    ) {
        self.networkService = networkService
        self.interlocutorsPhoneNamber = interlocutorsPhoneNamber
    }


    deinit {
        networkService.stopMessaging()
    }
}

// MARK: - ConversationInteractorInput

extension ConversationInteractor: ConversationInteractorInput {

    // MARK: Public

    func getMessages(page: Int, completion: @escaping (Result<[MessageModel], Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }

        let config = RequestConfigWithParser(
            request: MessagesTarget(
                phoneNumber1: phoneNumber,
                phoneNumber2: interlocutorsPhoneNamber,
                page: page
            ),
            parser: MessagesParser()
        )

        networkService.sendGetRequest(config: config) { result in
            switch result {
            case let .success(data):
                completion(.success(data))
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    func startMessaging() {
        networkService.startMessaging()
    }

    func setHandleMessageAction(_ action: ((MessageModel) -> Void)?) {
        networkService.setHandleMessageAction(action)
    }
}
