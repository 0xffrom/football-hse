//
//  RegistrationModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol RegistrationModuleInput: AnyObject {
    
}

public protocol RegistrationModuleOutput: AnyObject {
    func moduleWantsToGoToTheNextStep(_ module: RegistrationModuleInput)
}
