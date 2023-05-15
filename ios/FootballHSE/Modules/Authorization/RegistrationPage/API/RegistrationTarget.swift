//
//  RegistrationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

struct RegistrationTarget: RequestProtocol {
    let path = "api/Players/"

    var urlRequest: URLRequest?

    init(phoneNumber: String, name: String, roleId: Int) {
        let urlString = BaseURL.stringURL + path + phoneNumber
        guard let url = URL(string: urlString) else {
            return
        }
        self.urlRequest = URLRequest(url: url)

        let json: [String: Any] = ["phoneNumber": phoneNumber,
                                   "name": name,
                                   "isRegistered": true,
                                   "hseRole": roleId]
        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = "PUT"
    }
}
