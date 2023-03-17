//
//  RootCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.02.2023.
//

import Foundation
import UIKit

final class RootCoordinator {

    // MARK: Private Properties

    private unowned let window: UIWindow

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private weak var rootViewController: UIViewController?
    private weak var parentNavigationController: UINavigationController?

    private let networkService: INetworkService = NetworkService()

    private let currentUserConfig: CurrentUserConfig

    // MARK: Lifecycle

    init(
        parentNavigationController: UINavigationController,
        window: UIWindow,
        currentUserConfig: CurrentUserConfig,
        finishHandler: (() -> Void)?
    ) {
        self.parentNavigationController = parentNavigationController
        self.window = window
        self.currentUserConfig = currentUserConfig
        finishHandlers.append(finishHandler)
    }

    // MARK: Private

    private func startAuthorizationFlow() {
        guard let parentNavigationController = parentNavigationController else { return }

        let finishHandler: () -> Void = { [weak self] in
            guard let self = self else { return }
            self.childCoordinators.removeCoordinator(ofType: AuthorizationCoordinator.self)
        }

        let coordinator = AuthorizationCoordinator(
            parentNavigationController: parentNavigationController,
            window: window,
            networkService: networkService,
            currentUserConfig: currentUserConfig,
            finishHandler: finishHandler
        )
        coordinator.start(animated: true)
        childCoordinators.append(coordinator)
    }

    private func finishAuthorizationFlow() {
        guard let authCoordinator = childCoordinators.getCoordinator(ofType: AuthorizationCoordinator.self) else {
            return
        }
        authCoordinator.finish(animated: true) { [weak self] in
            guard let self = self else { return }
            self.childCoordinators.removeCoordinator(ofType: AuthorizationCoordinator.self)
        }
    }
}

// MARK: Coordinatable

extension RootCoordinator: Coordinatable {

    func start(animated: Bool) {
        startAuthorizationFlow()
    }

    func finish(animated: Bool, completion: (() -> Void)?) {
        guard !finishHandlers.isEmpty else {
            childCoordinators.finishAll(animated: animated, completion: completion)
            return
        }
        completion.map { finishHandlers.append($0) }
    }
}
