//
//  AuthorizationPhoneEnteringModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol AuthorizationPhoneEnteringModuleInput: AnyObject {
    
}

public protocol AuthorizationPhoneEnteringModuleOutput: AnyObject {
    func moduleWantsToGoToTheNextStep(_ module: AuthorizationPhoneEnteringModuleInput)
}
