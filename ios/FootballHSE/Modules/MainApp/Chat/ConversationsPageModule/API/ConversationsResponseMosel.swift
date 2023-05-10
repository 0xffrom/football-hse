//
//  ConversationsResponseMosel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 09.05.2023.
//

import Foundation

public struct LastMessageResponseMosel: Codable {
    let id: Int
    let sendTime: String
    let text: String?
    let isRead: Bool
    let sender: String
    let receiver: String
}

public struct ConversationResponseMosel: Codable {
    let id: Int
    let lastMessage: LastMessageResponseMosel?
    let phoneNumber1: String
    let name1: String?
    let photo1: String?
    let phoneNumber2: String
    let name2: String?
    let photo2: String?
}
