//
//  CreateTeamApplicationsTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 21.04.2023.
//

import Foundation

struct CreateTeamApplicationsTarget: RequestProtocol {
    let path = "api/TeamApplications"

    var urlRequest: URLRequest?

    init(position: Int, tournaments: Int) {
        let urlString = BaseURL.stringURL + path
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)

        let json: [String: Any] = ["teamId": CurrentTeamConfig.shared.id as Any,
                                   "name": CurrentTeamConfig.shared.name as Any,
                                   "logo": CurrentTeamConfig.shared.photoUrl as Any,
                                   "contact": CurrentUserConfig.shared.phoneNumber as Any,
                                   "playerPosition": position,
                                   "tournaments": tournaments,
                                   "description": CurrentTeamConfig.shared.about as Any]
        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = HttpMethod.POST.rawValue
    }
}
