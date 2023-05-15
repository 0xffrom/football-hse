//
//  DeletePlayerApplicationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 08.05.2023.
//

import Foundation

struct DeletePlayerApplicationTarget: RequestProtocol {
    let path = "api/PlayerApplications/"

    var urlRequest: URLRequest?

    init(id: Int) {
        let urlString = BaseURL.stringURL + path + "\(id)"
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
