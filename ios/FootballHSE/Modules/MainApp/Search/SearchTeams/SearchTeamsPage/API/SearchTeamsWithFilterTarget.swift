//
//  SearchTeamsWithFilterTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 19.04.2023.
//

import Foundation

struct SearchTeamsWithFilterTarget: RequestProtocol {
    let path = "api/TeamApplications/filters"

    var urlRequest: URLRequest?

    init(position: Int, tournaments: Int) {
        let urlString = BaseURL.stringURL + path + "?position=\(position)&tournaments=\(tournaments)"
        guard let url = URL(string: urlString) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
