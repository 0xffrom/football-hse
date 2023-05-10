//
//  ChatCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 08.05.2023.
//

import UIKit

final class ChatCoordinator {

    // MARK: Private Properties

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private unowned let window: UIWindow

    private weak var parentTabBarController: UITabBarController?
    private weak var parentNavigationController: UINavigationController?

    private weak var input: SearchTeamsPageModuleInput?

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(
        parentTabBarController: UITabBarController,
        window: UIWindow,
        networkService: INetworkService
    ) {
        self.parentTabBarController = parentTabBarController
        self.window = window
        self.networkService = networkService
    }
}

// MARK: Coordinatable

extension ChatCoordinator: Coordinatable {

    func start(animated: Bool) {
        let builder = ConversationsModuleBuilder(
            output: self,
            networkService: networkService
        )
        let viewController = builder.build()

        let navigationController = UINavigationController(rootViewController: viewController)
        parentNavigationController = navigationController

        parentTabBarController?.addViewController(navigationController)
    }

    func finish(animated: Bool, completion: (() -> Void)?) {
        guard !finishHandlers.isEmpty else {
            childCoordinators.finishAll(animated: animated, completion: completion)
            return
        }
        completion.map { finishHandlers.append($0) }
    }
}

extension ChatCoordinator: ConversationsModuleOutput {

    func wantsToOpenConversation(phoneNumber: String, name: String?, image: UIImage?) {
        let builder = ConversationModuleBuilder(
            output: self,
            networkService: networkService,
            interlocutorsPhoneNamber: phoneNumber,
            interlocutorsName: name,
            image: image
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}

extension ChatCoordinator: ConversationModuleOutput{}
