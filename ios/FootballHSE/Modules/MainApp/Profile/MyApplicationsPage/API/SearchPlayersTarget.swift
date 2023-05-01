//
//  SearchPlayersTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 21.04.2023.
//

import Foundation

struct SearchPlayersTarget: RequestProtocol {
    let path = "api/PlayerApplications"

    var urlRequest: URLRequest?

    init() {
        let urlString = BaseURL.stringURL + path
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
