//
//  GetProfileTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 04.05.2023.
//

import Foundation

struct GetProfileTarget: RequestProtocol {
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
