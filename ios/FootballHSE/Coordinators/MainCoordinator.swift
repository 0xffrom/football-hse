//
//  MainCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 11.03.2023.
//

import Foundation
import UIKit

final class MainCoordinator {

    enum Tab {
        case searchTeams
        case searchPlayers
        case chat
        case profile
    }

    private weak var mainInput: MainModuleInput?

    private unowned let window: UIWindow

    private weak var rootTabBarController: UITabBarController?

    private let networkService: INetworkService

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    init(
        window: UIWindow,
        networkService: INetworkService
    ) {
        self.window = window
        self.networkService = networkService
    }

    deinit {
        networkService.stopMessaging()
    }

    private func configureTabsForPlayer() {
        addSearchTeamsTab()
        addChatTab()
        addProfileTab()
    }

    private func configureTabsForCaptain() {
        addSearchTeamsTab()
        addSearchPlayersTab()
        addChatTab()
        addProfileTab()
    }

    private func addSearchTeamsTab() {
        guard let rootTabBarController = rootTabBarController else {
            return
        }

        let searchTeamsCoordinator = SearchTeamsCoordinator(
            parentTabBarController: rootTabBarController,
            window: window,
            networkService: networkService
        )
        childCoordinators.append(searchTeamsCoordinator)

        searchTeamsCoordinator.start(animated: false)
    }

    private func addSearchPlayersTab() {
        guard let rootTabBarController = rootTabBarController else {
            return
        }

        let searchTeamsCoordinator = SearchPlayersCoordinator(
            parentTabBarController: rootTabBarController,
            window: window,
            networkService: networkService
        )
        childCoordinators.append(searchTeamsCoordinator)

        searchTeamsCoordinator.start(animated: false)
    }

    private func addChatTab() {
        guard let rootTabBarController = rootTabBarController else {
            return
        }

        let chatCoordinator = ChatCoordinator(
            parentTabBarController: rootTabBarController,
            window: window,
            networkService: networkService
        )
        childCoordinators.append(chatCoordinator)

        chatCoordinator.start(animated: false)
    }

    private func addProfileTab() {
        guard let rootTabBarController = rootTabBarController else {
            return
        }

        let profileCoordinator = ProfileCoordinator(
            parentTabBarController: rootTabBarController,
            networkService: networkService,
            window: window,
            resignCapitanStatusAction: { [weak self] in
                self?.removeSearchPlayersTab()
            }
        )
        childCoordinators.append(profileCoordinator)

        profileCoordinator.start(animated: false)
    }

    private func removeSearchPlayersTab() {
        let searchPlayersCoordinator = childCoordinators.first {
            $0 as? SearchPlayersCoordinator != nil
        }
        if let coordinator = searchPlayersCoordinator as? SearchPlayersCoordinator {
            coordinator.finish(animated: true)
        }
        if let vcs = rootTabBarController?.viewControllers, vcs.count == 4 {
            rootTabBarController?.viewControllers?.remove(at: 1)
        }
    }
}

extension MainCoordinator: Coordinatable {

    func start(animated: Bool) {
        let moduleBuilder = MainModuleBuilder(output: self)
        rootTabBarController = moduleBuilder.build()

        finishHandlers.append { [weak self] in
            self?.rootTabBarController?.setViewControllers([], animated: false)
        }

        if CurrentUserConfig.shared.isCaptain ?? false {
            configureTabsForCaptain()
        } else {
            configureTabsForPlayer()
        }

        switchViewController(rootTabBarController, in: window)
    }

    func finish(animated: Bool, completion: (() -> Void)?) {
        guard !finishHandlers.isEmpty else {
            childCoordinators.finishAll(animated: animated, completion: completion)
            return
        }
        completion.map { finishHandlers.append($0) }
    }
}

extension MainCoordinator: MainModuleOutput {}
