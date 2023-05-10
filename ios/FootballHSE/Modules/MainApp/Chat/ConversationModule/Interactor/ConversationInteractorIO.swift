//
//  ConversationInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol ConversationInteractorInput: AnyObject {
    func getMessages(page: Int, completion: @escaping (Result<[MessageModel], Error>) -> Void)
    func setHandleMessageAction(_ action: ((MessageModel) -> Void)?)
    func startMessaging()
}

protocol ConversationInteractorOutput: AnyObject {}
