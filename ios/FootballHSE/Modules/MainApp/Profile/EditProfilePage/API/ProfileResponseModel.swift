//
//  ProfileResponseModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 04.05.2023.
//

import Foundation

struct ProfileResponseModel: Codable {
    let phoneNumber: String
    let name: String
    let isCaptain: Bool
    let isRegistered: Bool
    let about: String?
    let contact: String?
    let footballExperience: String?
    let tournamentExperience: String?
    let photo: String?
    let hseRole: Int?
    let refreshToken: String?
    let refreshTokenExpiryTime: String?
    let code: String?
    let saltForCode: String?
}
