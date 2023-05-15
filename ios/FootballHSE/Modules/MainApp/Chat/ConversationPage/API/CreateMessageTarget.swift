//
//  CreateMessageTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 15.05.2023.
//

import Foundation

struct CreateMessageTarget: RequestProtocol {
    let path = "api/Messages"

    var urlRequest: URLRequest?

    init(message: MessageModel) {
        guard let url = URL(string: BaseURL.stringURL + path) else {
            return
        }
        urlRequest = URLRequest(url: url)
        urlRequest?.httpMethod = "POST"
        let json: [String: Any] = ["id": message.id,
                                   "sendTime": message.sendTime,
                                   "text": message.text as Any,
                                   "isRead": message.isRead,
                                   "sender": message.sender,
                                   "receiver": message.receiver]
        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        urlRequest?.httpBody = jsonData
    }
}
