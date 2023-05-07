//
//  EditTeamPhotoTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 04.05.2023.
//

import Foundation

struct EditTeamPhotoTarget: RequestProtocol {
    let path = "api/Image/Team/"

    var urlRequest: URLRequest?

    init(id: Int) {
        guard let url = URL(string: BaseURL.stringURL + path + "\(id)") else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
