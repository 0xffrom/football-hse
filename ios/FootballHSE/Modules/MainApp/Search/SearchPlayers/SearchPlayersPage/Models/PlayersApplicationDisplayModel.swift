//
//  PlayersApplicationDisplayModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import UIKit

public struct PlayersApplicationDisplayModel {
    let id: Int
    let playerPhoneNumber: String
    let name: String
    let photo: UIImage?
    let contact: String?
    let footballPosition: [PlayerPosition]
    let tournaments: String?
    let footballExperience: String?
    let tournamentExperience: String?
}
