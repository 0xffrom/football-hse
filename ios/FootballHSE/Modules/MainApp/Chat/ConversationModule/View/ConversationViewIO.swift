//
//  ConversationViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol ConversationViewInput: AnyObject {
    func setupLoadingState()
    func setupDataState(with data: [MessageModel])
    func setupErrorState()
    func setupEmptyState()
}

protocol ConversationViewOutput: AnyObject {
    func viewDidLoad()
    func viewWantToLoadMoreData()
    func setHandleMessageAction(_ action: ((MessageModel) -> Void)?)
}
