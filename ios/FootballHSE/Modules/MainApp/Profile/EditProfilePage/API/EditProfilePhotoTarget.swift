//
//  EditProfilePhotoTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 02.05.2023.
//

import Foundation

struct EditProfilePhotoTarget: RequestProtocol {
    let path = "api/Image/Player/"

    var urlRequest: URLRequest?

    init(phoneNumber: String) {
        guard let url = URL(string: BaseURL.stringURL + path + phoneNumber) else {
            return
        }
        urlRequest = URLRequest(url: url)
    }
}
