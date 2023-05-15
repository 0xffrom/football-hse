//
//  ConversationModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class ConversationModuleBuilder {

    // MARK: Private Properties

    private weak var output: ConversationModuleOutput?
    
    private let networkService: INetworkService

    private let interlocutorsPhoneNamber: String
    private let interlocutorsName: String?
    private let interlocutorsImageURL: String?
    private let conversationID: Int
    private let image: UIImage?
    private let lastMessage: MessageModel?

    // MARK: Lifecycle

    public init(
        output: ConversationModuleOutput?,
        networkService: INetworkService,
        interlocutorsPhoneNamber: String,
        interlocutorsName: String?,
        interlocutorsImageURL: String?,
        conversationID: Int,
        image: UIImage?,
        lastMessage: MessageModel?
    ) {
        self.output = output
        self.networkService = networkService
        self.interlocutorsPhoneNamber = interlocutorsPhoneNamber
        self.interlocutorsName = interlocutorsName
        self.interlocutorsImageURL = interlocutorsImageURL
        self.conversationID = conversationID
        self.image = image
        self.lastMessage = lastMessage
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = ConversationInteractor(
            networkService: networkService,
            interlocutorsPhoneNamber: interlocutorsPhoneNamber,
            interlocutorsName: interlocutorsName,
            interlocutorsPhoto: interlocutorsImageURL,
            conversationID: conversationID,
            lastMessage: lastMessage
        )
        let presenter = ConversationPresenter(interactor: interactor)

        let viewController = ConversationViewController()

        viewController.output = presenter

        if interlocutorsPhoneNamber == "88888888888" {
            viewController.navigationTitle = "Поддержка"
        } else {
            viewController.navigationTitle = interlocutorsName
        }
        if interlocutorsPhoneNamber == "88888888888" {
            viewController.image = R.image.supportIcon()!
        } else {
            viewController.image = image
        }
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
