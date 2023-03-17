//
//  AuthorizationPhoneEnteringInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol AuthorizationPhoneEnteringInteractorInput: AnyObject {
    func authorize(with phoneNumber: String, completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol AuthorizationPhoneEnteringInteractorOutput: AnyObject {}
