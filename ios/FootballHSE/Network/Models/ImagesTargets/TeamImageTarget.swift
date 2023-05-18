//
//  TeamImageTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

struct TeamImageTarget: RequestProtocol {
    let path = "api/Image/Team/"

    var urlRequest: URLRequest?

    init(teamId: String) {
        let urlString = BaseURL.stringURL + path + teamId
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}

