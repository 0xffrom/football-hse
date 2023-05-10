//
//  ConversationsModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public protocol ConversationsModuleInput: AnyObject {}

public protocol ConversationsModuleOutput: AnyObject {
    func wantsToOpenConversation(phoneNumber: String, name: String?, image: UIImage?)
}

