//
//  CreateTeamSearchApplicationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

struct CreateTeamSearchApplicationTarget: RequestProtocol {

    let path = "api/TeamApplications"

    var urlRequest: URLRequest?

    init() {
        let urlString = BaseURL.stringURL + path
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
        urlRequest?.httpMethod = HttpMethod.POST.rawValue
    }
}
