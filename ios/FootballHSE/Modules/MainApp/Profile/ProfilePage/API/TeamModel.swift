//
//  TeamModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import Foundation

public struct TeamModel: Codable {
    let id: Int
    let name: String
    let captainPhoneNumber: String
    let logo: String?
    let about: String?
    let status: Int
}
