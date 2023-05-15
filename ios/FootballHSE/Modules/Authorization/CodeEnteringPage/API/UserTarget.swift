//
//  UserTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 26.03.2023.
//

import Foundation

struct UserTarget: RequestProtocol {
    let path = "api/Players/"

    var urlRequest: URLRequest?

    init(phoneNumber: String) {
        let urlString = BaseURL.stringURL + path + phoneNumber
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
        urlRequest?.httpMethod = "GET"
    }
}
