//
//  PlayersApplicationInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol PlayersApplicationInteractorInput: AnyObject {
    func getChat(completion: @escaping (Result<ConversationResponseMosel, Error>) -> Void)
}

protocol PlayersApplicationInteractorOutput: AnyObject {
    
}
