//
//  PlayerApplicationResponseModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 21.04.2023.
//

import Foundation

struct PlayerApplicationResponseModel: Codable {
    let id: Int
    let playerPhoneNumber: String
    let footballPosition: Int
    let preferredTournaments: Int
    let footballExperience: String?
    let tournamentExperience: String?
    let contact: String?
    let name: String
    let photo: String?
}
