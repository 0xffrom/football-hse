//
//  User.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 26.03.2023.
//

import Foundation

struct User: Codable {
    let phoneNumber: String
    let name: String
    let isCaptain: Bool
    let isRegistered: Bool
    let about: String?
    let contact: String?
    let footballExperience: String?
    let tournamentExperience: String?
    let photo: String?
    let hseRole: Int
    let applicationId: Int
}
