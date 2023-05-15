//
//  ConversationsInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol ConversationsInteractorInput: AnyObject {
    func getChats(completion: @escaping (Result<[ConversationDisplayModel], Error>) -> Void)
    func getImageURLForChat(with id: Int) -> String?
    func getLastMessageForChat(with id: Int) -> MessageModel?
}

protocol ConversationsInteractorOutput: AnyObject {}
