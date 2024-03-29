//
//  AuthorizationCodeEnteringTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

struct AuthorizationCodeEnteringTarget: RequestProtocol {
    let path = "api/Authentication/refresh/"

    var urlRequest: URLRequest?

    init(phoneNumber: String, code: String) {
        let urlString = BaseURL.stringURL + path + phoneNumber + "/" + code
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
        urlRequest?.httpMethod = "POST"
    }
}
