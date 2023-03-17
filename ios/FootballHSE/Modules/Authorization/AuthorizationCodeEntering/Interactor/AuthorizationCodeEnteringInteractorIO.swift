//
//  AuthorizationCodeEnteringInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol AuthorizationCodeEnteringInteractorInput: AnyObject {
    func sendCode(code: String, completion: @escaping (Result<TokensModel, Error>) -> Void)
}

protocol AuthorizationCodeEnteringInteractorOutput: AnyObject {}
