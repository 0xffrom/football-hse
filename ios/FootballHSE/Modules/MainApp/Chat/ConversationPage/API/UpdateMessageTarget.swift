//
//  UpdateMessageTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 15.05.2023.
//

import Foundation

struct UpdateMessageTarget: RequestProtocol {
    let path = "api/Messages/"

    var urlRequest: URLRequest?

    init(message: MessageModel) {
        guard let url = URL(string: BaseURL.stringURL + path + "\(message.id)") else {
            return
        }
        urlRequest = URLRequest(url: url)
        let messageJson: [String: Any] = ["id": message.id as Any,
                                          "sendTime": message.sendTime as Any,
                                          "text": message.text as Any,
                                          "isRead": message.isRead as Any,
                                          "sender": message.sender as Any,
                                          "receiver": message.receiver as Any]

        let jsonData = try? JSONSerialization.data(withJSONObject: messageJson)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = "PUT"
    }
}
