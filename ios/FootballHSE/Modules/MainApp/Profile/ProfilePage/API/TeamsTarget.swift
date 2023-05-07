//
//  TeamsTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import Foundation

struct TeamsTarget: RequestProtocol {
    let path = "api/Teams"

    var urlRequest: URLRequest?

    init() {
        let urlString = BaseURL.stringURL + path
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
