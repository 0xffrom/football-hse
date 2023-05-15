//
//  ConversationsTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 19.04.2023.
//

import Foundation

struct GetConversationsTarget: RequestProtocol {
    let path = "api/Chats/user/"

    var urlRequest: URLRequest?

    init(phoneNumber: String) {
        let urlString = BaseURL.stringURL + path + phoneNumber
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
