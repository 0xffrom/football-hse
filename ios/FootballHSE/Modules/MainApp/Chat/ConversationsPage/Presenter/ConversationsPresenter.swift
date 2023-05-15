//
//  ConversationsPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

final class ConversationsPresenter {

    // MARK: Public Properties

    weak var view: ConversationsViewInput?
    weak var moduleOutput: ConversationsModuleOutput?
    
    // MARK: Private Properties

    private let interactor: ConversationsInteractorInput

    // MARK: Lifecycle

    init(interactor: ConversationsInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private

    private func handleRequest(_ result: Result<[ConversationDisplayModel], Error>) {
        switch result {
        case .success(let data):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                if data.isEmpty {
                    view.setupEmptyState()
                } else {
                    view.setupDataState(with: data)
                }
            }
        case .failure(_):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                view.setupErrorState()
            }
        }
    }

    private func getChats() {
        interactor.getChats { [weak self] result in
            self?.handleRequest(result)
        }
    }
}

// MARK: - ConversationsModuleInput

extension ConversationsPresenter: ConversationsModuleInput {}

// MARK: - ConversationsInteractorOutput

extension ConversationsPresenter: ConversationsInteractorOutput {}

// MARK: - ConversationsViewOutput

extension ConversationsPresenter: ConversationsViewOutput {

    func viewDidLoad() {
        view?.setupLoadingState()
        getChats()
    }

    func viewDidAppear() {
        getChats()
    }

    func viewStartRefreshing() {
        getChats()
    }

    func wantsToOpenConversation(phoneNumber: String, name: String?, image: UIImage?, conversationID: Int) {
        moduleOutput?.wantsToOpenConversation(
            phoneNumber: phoneNumber,
            name: name,
            image: image,
            interlocutorsImageURL: interactor.getImageURLForChat(with: conversationID),
            conversationID: conversationID,
            lastMessage: interactor.getLastMessageForChat(with: conversationID)
        )
    }
}
