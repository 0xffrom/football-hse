//
//  RegistrationInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol RegistrationInteractorInput: AnyObject {
    func registerUser(name: String, roleId: Int, completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol RegistrationInteractorOutput: AnyObject {
    
}
