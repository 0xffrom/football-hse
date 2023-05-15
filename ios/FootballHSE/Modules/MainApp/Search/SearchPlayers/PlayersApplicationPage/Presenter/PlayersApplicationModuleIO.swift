//
//  PlayersApplicationModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public protocol PlayersApplicationModuleInput: AnyObject {
    
}

public protocol PlayersApplicationModuleOutput: AnyObject {
    func back()
    func wantsToOpenConversation(phoneNumber: String?, name: String?, image: UIImage?, interlocutorsImageURL: String?, conversationID: Int, lastMessage: MessageModel?)
}
