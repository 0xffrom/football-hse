//
//  TeamApplicationDisplayModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import UIKit

public struct TeamApplicationDisplayModel {
    let id: Int
    let teamId: Int
    let name: String
    let logo: UIImage?
    let contact: String?
    let playerPosition: [PlayerPosition]
    let tournaments: String?
    let description: String?
}
