//
//  CurrentTeamConfig.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import UIKit

public class CurrentTeamConfig {

    static let shared = CurrentTeamConfig()

    var id: Int?
    var photo: UIImage?
    var photoUrl: String?
    var name: String?
    var about: String?
    var status: Int?

    private init() { }

    static func clear() {
        shared.id = nil
        shared.photo = nil
        shared.photoUrl = nil
        shared.name = nil
        shared.about = nil
        shared.status = nil
    }
}
