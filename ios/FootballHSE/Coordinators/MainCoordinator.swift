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

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    init(window: UIWindow) {
        self.window = window
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
            parentTabBarController: rootTabBarController
        )
        childCoordinators.append(searchTeamsCoordinator)

        searchTeamsCoordinator.start(animated: false)
    }

    private func addSearchPlayersTab() {
    }

    private func addChatTab() {
    }

    private func addProfileTab() {
        guard let rootTabBarController = rootTabBarController else {
            return
        }

        let profileCoordinator = ProfileCoordinator(
            parentTabBarController: rootTabBarController
        )
        childCoordinators.append(profileCoordinator)

        profileCoordinator.start(animated: false)
    }
}

extension MainCoordinator: Coordinatable {

    func start(animated: Bool) {
        let moduleBuilder = MainModuleBuilder(output: self)
        rootTabBarController = moduleBuilder.build()

        finishHandlers.append { [weak self] in
            self?.rootTabBarController?.setViewControllers([], animated: false)
        }

        configureTabsForPlayer()

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
