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
        interactor.startMessaging()
    }

    func viewWantToLoadMoreData() {
        currentPage += 1
        getMessages(page: currentPage)
    }

    func setHandleMessageAction(_ action: ((MessageModel) -> Void)?) {
        interactor.setHandleMessageAction(action)
    }
}
