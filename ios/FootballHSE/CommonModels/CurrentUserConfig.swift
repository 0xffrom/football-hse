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

    init() {}
}
