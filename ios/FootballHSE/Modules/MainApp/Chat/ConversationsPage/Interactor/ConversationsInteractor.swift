//
//  ConversationsInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation
import UIKit

final class ConversationsInteractor {

    // MARK: Public Properties
    
    weak var output: ConversationsInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    private var chats: [ConversationResponseMosel] = []

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    private func convertResponseToDisplayModel(_ response: [ConversationResponseMosel], completion: @escaping (Result<[ConversationDisplayModel], Error>) -> Void) {
        DispatchQueue.global().async { [weak self] in
            guard let self else { return }

            Task {
                self.chats = response
                var displayModel: [ConversationDisplayModel] = []

                let sortedData = self.sortdByDate(response)

                for responseItem in sortedData {
                    let currentUserIsFirst = responseItem.phoneNumber1 == CurrentUserConfig.shared.phoneNumber

                    let imageURL = currentUserIsFirst ? responseItem.photo2 : responseItem.photo1
                    let image = await self.networkService.downlandImage(url: imageURL)

                    let name = currentUserIsFirst ? responseItem.name2 : responseItem.name1
                    let phoneNumber = currentUserIsFirst ? responseItem.phoneNumber2 : responseItem.phoneNumber1

                    var check: ConversationDisplayModel.CheckStatus?
                    if responseItem.lastMessage == nil ||
                        responseItem.lastMessage?.receiver == CurrentUserConfig.shared.phoneNumber {
                        check = .noChecks
                    } else if responseItem.lastMessage?.sender == CurrentUserConfig.shared.phoneNumber &&
                                responseItem.lastMessage?.isRead ?? false {
                        check = .twoChecks
                    } else {
                        check = .oneCheck
                    }

                    let unreadMessageSign =
                    responseItem.lastMessage?.receiver == CurrentUserConfig.shared.phoneNumber &&
                    !(responseItem.lastMessage?.isRead ?? true)

                    let isSupport = responseItem.phoneNumber2 == "88888888888" || responseItem.phoneNumber1 == "88888888888"

                    let displayItem = ConversationDisplayModel(
                        id: responseItem.id,
                        isSupport: isSupport,
                        phoneNumber: phoneNumber,
                        name: name,
                        lastMessage: responseItem.lastMessage?.text,
                        photo: image,
                        unreadMessageSign: unreadMessageSign ,
                        check: check ?? .noChecks,
                        time: self.prepareDateForDisplaying(dateString: responseItem.lastMessage?.sendTime)
                    )

                    if responseItem.name2 != nil && responseItem.name1 != nil {
                        displayModel.append(displayItem)
                    }
                }

                completion(.success(displayModel))
            }
        }
    }

    private func sortdByDate(_ data: [ConversationResponseMosel]) -> [ConversationResponseMosel] {
        return data.sorted {
            if ($1.phoneNumber1 == "88888888888" || $1.phoneNumber2 == "88888888888") {
                return false
            }
            guard let date1 = $0.lastMessage?.sendTime.getDateFromString() else {
                return true
            }
            guard let date2 = $1.lastMessage?.sendTime.getDateFromString() else {
                return true
            }
            return date1 > date2
        }
    }

    private func prepareDateForDisplaying(dateString: String?) -> String? {
        guard let date = dateString?.getDateFromString() else {
            return nil
        }
        return String.fromDateToString(from: date)
    }
}

// MARK: - ConversationsInteractorInput

extension ConversationsInteractor: ConversationsInteractorInput {

    // MARK: Public

    func getChats(completion: @escaping (Result<[ConversationDisplayModel], Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }

        let config = RequestConfigWithParser(
            request: GetConversationsTarget(phoneNumber: phoneNumber),
            parser: ConversationsParser()
        )

        networkService.sendGetRequest(config: config) {  [weak self] result in
            guard let self else { return }
            switch result {
            case let .success(data):
                self.convertResponseToDisplayModel(data, completion: completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }

    func getImageURLForChat(with id: Int) -> String? {
        var chat: ConversationResponseMosel?
        chat = self.chats.first(where: {
            $0.id == id
        })
        return chat?.phoneNumber1 == CurrentUserConfig.shared.phoneNumber ? chat?.photo2 : chat?.photo1
    }

    func getLastMessageForChat(with id: Int) -> MessageModel? {
        var chat: ConversationResponseMosel?
        chat = self.chats.first(where: {
            $0.id == id
        })
        return chat?.lastMessage
    }
}
