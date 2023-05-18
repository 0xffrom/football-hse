//
//  ConversationPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class ConversationPresenter {

    // MARK: Public Properties

    weak var view: ConversationViewInput?
    weak var moduleOutput: ConversationModuleOutput?
    
    // MARK: Private Properties

    private let interactor: ConversationInteractorInput
    private var currentPage = 0

    // MARK: Lifecycle

    init(interactor: ConversationInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private

    private func handleRequest(_ result: Result<[MessageModel], Error>) {
        switch result {
        case .success(let data):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                if data.isEmpty {
                    view.setupEmptyState()
                } else {
                    view.setupDataState(with: data.reversed())
                }
            }
        case .failure(_):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                view.setupErrorState()
            }
        }
    }

    private func getMessages(page: Int) {
        interactor.getMessages(page: page) { [weak self] result in
            self?.handleRequest(result)
        }
    }
}

// MARK: - ConversationModuleInput

extension ConversationPresenter: ConversationModuleInput {}

// MARK: - ConversationInteractorOutput

extension ConversationPresenter: ConversationInteractorOutput {}

// MARK: - ConversationViewOutput

extension ConversationPresenter: ConversationViewOutput {

    func viewDidLoad() {
        view?.setupLoadingState()
        getMessages(page: currentPage)
        interactor.startMessaging(handleMessageAction: { [weak self] message in
            self?.view?.displayReceivedMessage(message: message)
        })
        interactor.updateLastMessageInConversation()
    }

    func viewWantToLoadMoreData() {
        currentPage += 1
        getMessages(page: currentPage)
    }

    func sendMessage(_ message: String) {
        view?.disableSendMessageButton()
        interactor.sendMessage(message) { result in
            switch result {
            case .success(let message):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.displaySentMessage(message: message)
                    view.enableSendMessageButton()
                }
            case .failure(_):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.setupErrorState()
                    view.enableSendMessageButton()
                }
            }
        }
    }
}
