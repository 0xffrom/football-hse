//
//  CreateApplicationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 21.04.2023.
//

import Foundation

struct CreatePlayerApplicationsTarget: RequestProtocol {
    let path = "api/PlayerApplications"

    var urlRequest: URLRequest?

    init(position: Int, tournaments: Int) {
        let urlString = BaseURL.stringURL + path
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)

        let json: [String: Any] = ["playerPhoneNumber": CurrentUserConfig.shared.phoneNumber,
                                   "footballPosition": position,
                                   "preferredTournaments": tournaments,
                                   "faculty": "\(CurrentUserConfig.shared.hseRole!)",
                                   "footballExperience": CurrentUserConfig.shared.footballExperience,
                                   "tournamentExperience": CurrentUserConfig.shared.tournamentExperience,
                                   "contact": CurrentUserConfig.shared.contact,
                                   "name": CurrentUserConfig.shared.name,
                                   "photo": CurrentUserConfig.shared.photo]
        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = HttpMethod.POST.rawValue
    }
}
