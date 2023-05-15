//
//  ConversationDisplayModel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import UIKit

public struct ConversationDisplayModel {
    let id: Int
    enum CheckStatus {
        case oneCheck
        case twoChecks
        case noChecks
    }
    let isSupport: Bool
    let phoneNumber: String
    let name: String?
    let lastMessage: String?
    let photo: UIImage?
    let unreadMessageSign: Bool
    let check: CheckStatus
    let time: String?
}
