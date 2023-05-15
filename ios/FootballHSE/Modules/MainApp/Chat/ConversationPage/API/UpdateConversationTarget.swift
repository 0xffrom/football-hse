//
//  UpdateConversationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 15.05.2023.
//

import Foundation

struct UpdateConversationTarget: RequestProtocol {
    let path = "api/Chats/"

    var urlRequest: URLRequest?

    init(conversation: ConversationResponseMosel) {
        guard let url = URL(string: BaseURL.stringURL + path + "\(conversation.id)") else {
            return
        }
        urlRequest = URLRequest(url: url)
        let messageJson: [String: Any] = ["id": conversation.lastMessage?.id as Any,
                                          "sendTime": conversation.lastMessage?.sendTime as Any,
                                          "text": conversation.lastMessage?.text as Any,
                                          "isRead": conversation.lastMessage?.isRead as Any,
                                          "sender": conversation.lastMessage?.sender as Any,
                                          "receiver": conversation.lastMessage?.receiver as Any]
        let finalJson: [String: Any] = ["id": conversation.id,
                                        "lastMessage": messageJson,
                                        "phoneNumber1": conversation.phoneNumber1,
                                        "name1": conversation.name1 as Any,
                                        "photo1": conversation.photo1 as Any,
                                        "phoneNumber2": conversation.phoneNumber2,
                                        "name2": conversation.name2 as Any,
                                        "photo2": conversation.photo2 as Any]

        let jsonData = try? JSONSerialization.data(withJSONObject: finalJson)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = "PUT"
    }
}
