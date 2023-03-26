//
//  AuthorizationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

struct AuthorizationPhoneEnteringTarget: IRequest {
    let path = "api/Authentication/phone/"

    var urlRequest: URLRequest?

    init(phoneNumber: String) {
        guard let url = URL(string: BaseURL.stringURL + path + phoneNumber) else {
            return
        }
        urlRequest = URLRequest(url: url)
        urlRequest?.httpMethod = "POST"
    }
}
