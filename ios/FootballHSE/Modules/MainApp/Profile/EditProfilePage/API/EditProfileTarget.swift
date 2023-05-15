//
//  EditProfileTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

struct EditProfileTarget: RequestProtocol {
    let path = "api/Players/"

    var urlRequest: URLRequest?

    init(newInfo: EditProfileModel, oldInfo: ProfileResponseModel) {
        guard let url = URL(string: BaseURL.stringURL + path + oldInfo.phoneNumber) else {
            return
        }
        urlRequest = URLRequest(url: url)
        let json: [String: Any] = ["phoneNumber": oldInfo.phoneNumber,
                                   "name": newInfo.name as Any,
                                   "isCaptain": oldInfo.isCaptain,
                                   "isRegistered": oldInfo.isRegistered,
                                   "about": newInfo.about as Any,
                                   "contact": newInfo.contact as Any,
                                   "footballExperience": newInfo.footballExperience as Any,
                                   "tournamentExperience": newInfo.tournamentExperience as Any,
                                   "photo": oldInfo.photo as Any,
                                   "hseRole": oldInfo.hseRole as Any,
                                   "refreshToken": oldInfo.refreshToken as Any,
                                   "refreshTokenExpiryTime": oldInfo.refreshTokenExpiryTime as Any,
                                   "code": oldInfo.code as Any,
                                   "saltForCode": oldInfo.saltForCode as Any]

        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = "PUT"
    }
}
