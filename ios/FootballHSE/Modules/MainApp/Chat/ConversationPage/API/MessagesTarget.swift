//
//  ConversationsTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 19.04.2023.
//

import Foundation

struct MessagesTarget: RequestProtocol {
    let path = "api/Messages/"

    var urlRequest: URLRequest?

    init(phoneNumber1: String, phoneNumber2: String, page: Int) {
        let urlString = BaseURL.stringURL + path + phoneNumber1 + "/" + phoneNumber2 + "/" + "\(page)"
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
