//
//  AuthorizationCodeEnteringModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol AuthorizationCodeEnteringModuleInput: AnyObject {
    
}

public protocol AuthorizationCodeEnteringModuleOutput: AnyObject {
    func moduleWantsToGoToRegistration(_ module: AuthorizationCodeEnteringModuleInput)
    func moduleWantsToGoToMainApp(_ module: AuthorizationCodeEnteringModuleInput)
}
