//
//  ConversationsInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol ConversationsInteractorInput: AnyObject {
    func getChats(completion: @escaping (Result<[ConversationDisplayModel], Error>) -> Void)
}

protocol ConversationsInteractorOutput: AnyObject {}
