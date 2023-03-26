//
//  PlayerImageTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

struct PlayerImageTarget: IRequest {
    let path = "api/Image/Player/"

    var urlRequest: URLRequest?

    init(phoneNumber: String) {
        let urlString = BaseURL.stringURL + path + phoneNumber
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}

