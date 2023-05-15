//
//  ConversationsResponseMosel.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 09.05.2023.
//

import Foundation

public struct MessageModel: Codable {
    let id: Int
    let sendTime: String
    let text: String?
    let isRead: Bool
    let sender: String
    let receiver: String
}
