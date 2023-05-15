//
//  CurrentUserConfig.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

public class CurrentUserConfig {

    static let shared = CurrentUserConfig()

    var phoneNumber: String?
    var name: String?
    var isCaptain: Bool?
    var token: String?
    var refreshToken: String?
    var about: String?
    var contact: String?
    var footballExperience: String?
    var tournamentExperience: String?
    var photo: String?
    var hseRole: Int?
    var applicationId: Int?

    static func clear() {
        shared.phoneNumber = nil
        shared.name = nil
        shared.isCaptain = nil
        shared.token = nil
        shared.refreshToken = nil
        shared.about = nil
        shared.contact = nil
        shared.footballExperience = nil
        shared.tournamentExperience = nil
        shared.photo = nil
        shared.hseRole = nil
        shared.applicationId = nil
    }

    static func isAllInformationProvided() -> Bool {
        shared.name != nil && shared.about != nil &&
        shared.contact != nil && shared.footballExperience != nil &&
        shared.tournamentExperience != nil && shared.photo != nil
    }
}
