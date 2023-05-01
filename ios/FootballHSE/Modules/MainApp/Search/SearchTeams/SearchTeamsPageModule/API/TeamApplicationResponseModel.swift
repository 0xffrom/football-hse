//
//  TeamApplicationResponseModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

struct TeamApplicationResponseModel: Codable {
    let id: Int
    let teamId: Int
    let name: String
    let logo: String?
    let contact: String?
    let playerPosition: Int
    let tournaments: Int
    let description: String
}
