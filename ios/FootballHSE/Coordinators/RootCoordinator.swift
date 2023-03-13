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

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private weak var rootViewController: UIViewController?
    private weak var parentNavigationController: UINavigationController?

    // MARK: Lifecycle

    init(
        parentNavigationController: UINavigationController,
        finishHandler: (() -> Void)?
    ) {
        self.parentNavigationController = parentNavigationController
        finishHandlers.append(finishHandler)
    }
}

// MARK: Coordinatable

extension RootCoordinator: Coordinatable {

    func start(animated: Bool) {
        guard let parentNavigationController = parentNavigationController else { return }

        let finishHandler: () -> Void = { [weak self] in
            guard let self = self else { return }
            self.childCoordinators.removeCoordinator(ofType: AuthorizationCoordinator.self)
        }

        let coordinator = AuthorizationCoordinator(
            parentNavigationController: parentNavigationController,
            finishHandler: finishHandler
        )
        coordinator.start(animated: true)
        childCoordinators.append(coordinator)
    }

    func finish(animated: Bool, completion: (() -> Void)?) {
        guard !finishHandlers.isEmpty else {
            childCoordinators.finishAll(animated: animated, completion: completion)
            return
        }
        completion.map { finishHandlers.append($0) }
    }
}
