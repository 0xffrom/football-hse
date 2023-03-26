//
//  TeamApplicationDisplayModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

struct TeamApplicationDisplayModel: Codable {
    let id: Int
    let teamId: Int
    let name: String
    let logo: String?
    let contact: String
    let playerPosition: [PlayerPosition]
    let tournaments: Int
    let description: String
}
