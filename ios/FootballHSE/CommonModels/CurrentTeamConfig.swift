//
//  CurrentTeamConfig.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import UIKit

public class CurrentTeamConfig {

    static let shared = CurrentTeamConfig()

    var photo: UIImage?
    var name: String?
    var about: String?

    static func clear() {
        shared.photo = nil
        shared.name = nil
        shared.about = nil
    }
}
