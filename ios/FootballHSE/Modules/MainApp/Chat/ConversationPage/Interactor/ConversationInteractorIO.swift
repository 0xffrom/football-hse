//
//  ConversationInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol ConversationInteractorInput: AnyObject {
    func getMessages(page: Int, completion: @escaping (Result<[MessageModel], Error>) -> Void)
    func startMessaging(handleMessageAction: ((MessageModel) -> Void)?)
    func sendMessage(_ message: String, completion: @escaping (Result<MessageModel, Error>) -> Void)
    func updateLastMessageInConversation()
}

protocol ConversationInteractorOutput: AnyObject {}
