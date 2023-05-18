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
    private let interlocutorsName: String?
    private let interlocutorsPhoto: String?
    private let conversationID: Int
    private var lastMessage: MessageModel?

    // MARK: Lifecycle

    init(
        networkService: INetworkService,
        interlocutorsPhoneNamber: String,
        interlocutorsName: String?,
        interlocutorsPhoto: String?,
        conversationID: Int,
        lastMessage: MessageModel?
    ) {
        self.networkService = networkService
        self.interlocutorsPhoneNamber = interlocutorsPhoneNamber
        self.interlocutorsName = interlocutorsName
        self.interlocutorsPhoto = interlocutorsPhoto
        self.conversationID = conversationID
        self.lastMessage = lastMessage
    }

    deinit {
        networkService.stopMessaging()
    }

    private func createMessage(_ message: MessageModel, completion: @escaping (Result<MessageModel, Error>) -> Void) {
        let config = RequestConfig(request: CreateMessageTarget(message: message))
        networkService.sendPostRequest(config: config){ result in
            switch result {
            case .success(_):
                self.lastMessage = message
                completion(.success(message))
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    private func updateLastMessageInConversation(message: MessageModel) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }

        let conversation = ConversationResponseMosel(
            id: conversationID,
            lastMessage: message,
            phoneNumber1: phoneNumber,
            name1: CurrentUserConfig.shared.name,
            photo1: CurrentUserConfig.shared.photo,
            phoneNumber2: interlocutorsPhoneNamber,
            name2: interlocutorsName,
            photo2: interlocutorsPhoto
        )

        let config = RequestConfig(request: UpdateConversationTarget(conversation: conversation))
        networkService.sendPostRequest(config: config, competionHandler: nil)
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

    func startMessaging(handleMessageAction: ((MessageModel) -> Void)?) {
        networkService.setHandleMessageAction({ [weak self] message in
            handleMessageAction?(message)
            self?.lastMessage = message
        })
        networkService.startMessaging()
    }

    func sendMessage(_ message: String, completion: @escaping (Result<MessageModel, Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }
        let messageModel = MessageModel(
            id: 0,
            sendTime: Formatter.iso8601.string(from: Date()),
            text: message,
            isRead: false,
            sender: phoneNumber,
            receiver: interlocutorsPhoneNamber
        )

        networkService.sendMessage(
            phoneNumber: interlocutorsPhoneNamber,
            message: messageModel,
            completion: { [weak self] _ in
                self?.createMessage(messageModel, completion: completion)
            }
        )
    }

    func updateLastMessageInConversation() {
        guard let lastMessage else { return }
        if lastMessage.isRead == false && lastMessage.receiver == CurrentUserConfig.shared.phoneNumber {
            let message = MessageModel(
                id: lastMessage.id,
                sendTime: lastMessage.sendTime,
                text: lastMessage.text,
                isRead: true,
                sender: lastMessage.sender,
                receiver: lastMessage.receiver
            )
            let config = RequestConfig(request: UpdateMessageTarget(message: message))
            networkService.sendPostRequest(config: config, competionHandler: nil)
        }
    }
}
