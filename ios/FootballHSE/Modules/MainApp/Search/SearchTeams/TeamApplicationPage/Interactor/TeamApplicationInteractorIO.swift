//
//  TeamApplicationInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol TeamApplicationInteractorInput: AnyObject {
    func getChat(completion: @escaping (Result<ConversationResponseMosel, Error>) -> Void)
}

protocol TeamApplicationInteractorOutput: AnyObject {
    
}
