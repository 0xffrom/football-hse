//
//  DeleteTeamsTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import Foundation

struct DeleteTeamsTarget: RequestProtocol {
    let path = "api/Teams/"

    var urlRequest: URLRequest?

    init(id: Int) {
        let urlString = BaseURL.stringURL + path + "\(id)"
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest?.httpMethod = "DELETE"
        urlRequest = URLRequest(url: url)
    }
}
