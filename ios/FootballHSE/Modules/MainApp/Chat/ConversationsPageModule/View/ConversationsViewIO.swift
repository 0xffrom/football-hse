//
//  ConversationsViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

protocol ConversationsViewInput: AnyObject {
    func setupLoadingState()
    func setupDataState(with data: [ConversationDisplayModel])
    func setupErrorState()
    func setupEmptyState()
}

protocol ConversationsViewOutput: AnyObject {
    func viewDidLoad()
    func viewStartRefreshing()
    func wantsToOpenConversation(phoneNumber: String, name: String?, image: UIImage?)
}
