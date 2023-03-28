//
//  TeamApplicationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

struct TeamApplicationTarget: IRequest {
    let path = "api/TeamApplications"

    var urlRequest: URLRequest?

    init() {
        let urlString = BaseURL.stringURL + path
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
