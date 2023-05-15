//
//  PlayersApplicationPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

final class PlayersApplicationPresenter {

    // MARK: Public Properties

    weak var view: PlayersApplicationViewInput?
    weak var moduleOutput: PlayersApplicationModuleOutput?
    
    // MARK: Private Properties

    private let interactor: PlayersApplicationInteractorInput

    private let phoneNumber: String?
    private let name: String?
    private let image: UIImage?

    // MARK: Lifecycle

    init(interactor: PlayersApplicationInteractorInput,
         phoneNumber: String?,
         name: String?,
         image: UIImage?
    ) {
        self.interactor = interactor
        self.phoneNumber = phoneNumber
        self.name = name
        self.image = image
    }
}

// MARK: - PlayersApplicationModuleInput

extension PlayersApplicationPresenter: PlayersApplicationModuleInput {}

// MARK: - PlayersApplicationInteractorOutput

extension PlayersApplicationPresenter: PlayersApplicationInteractorOutput {}

// MARK: - PlayersApplicationViewOutput

extension PlayersApplicationPresenter: PlayersApplicationViewOutput {

    func openChat() {
        view?.setLoadingState()
        interactor.getChat { [weak self] result in
            guard let self = self, let view = self.view else { return }
            switch result {
            case .success(let chat):
                let imageURL = CurrentUserConfig.shared.phoneNumber == chat.phoneNumber1 ? chat.photo2 : chat.photo1
                DispatchQueue.main.asyncAfter(deadline: .now() + 1) { [weak self] in
                    guard let self = self else { return }
                    view.removeLoadingState()
                    self.moduleOutput?.wantsToOpenConversation(
                        phoneNumber: self.phoneNumber,
                        name: self.name,
                        image: self.image,
                        interlocutorsImageURL: imageURL,
                        conversationID: chat.id,
                        lastMessage: chat.lastMessage
                    )
                }
            case .failure(_):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.removeLoadingState()
                    view.showAlert()
                }
            }
        }
    }
}
