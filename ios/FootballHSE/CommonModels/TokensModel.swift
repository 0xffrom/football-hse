//
//  TokensModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

struct TokensModel: Codable {
    let token: String
    let refreshToken: String
    let phoneNumber: String
    let isCaptain: Bool
    let isRegistered: Bool
}
