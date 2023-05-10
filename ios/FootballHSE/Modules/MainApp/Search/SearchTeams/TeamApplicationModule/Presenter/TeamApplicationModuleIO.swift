//
//  TeamApplicationModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public protocol TeamApplicationModuleInput: AnyObject {
    
}

public protocol TeamApplicationModuleOutput: AnyObject {
    func wantsToOpenConversation(phoneNumber: String?, name: String?, image: UIImage?)
    func back() 
}
