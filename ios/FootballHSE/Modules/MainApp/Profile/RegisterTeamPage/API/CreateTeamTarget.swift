//
//  CreateTeamTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 04.05.2023.
//

import Foundation

struct CreateTeamTarget: RequestProtocol {
    let path = "api/Teams"

    var urlRequest: URLRequest?

    init(team: CreateTeamModel) {
        guard let url = URL(string: BaseURL.stringURL + path) else {
            return
        }
        urlRequest = URLRequest(url: url)
        let json: [String: Any] = ["name": team.name as Any,
                                   "captainPhoneNumber": CurrentUserConfig.shared.phoneNumber as Any,
                                   "captainName": CurrentUserConfig.shared.name as Any,
                                   "about": team.about as Any,
                                   "status": 0]

        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = "POST"
    }
}
