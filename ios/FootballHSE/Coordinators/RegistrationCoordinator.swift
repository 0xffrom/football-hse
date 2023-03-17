//
//  RegistrationCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation
import UIKit

final class RegistrationCoordinator {

    private unowned let window: UIWindow

    private weak var rootController: UIViewController?

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private let networkService: INetworkService

    private let currentUserConfig: CurrentUserConfig

    init(
        window: UIWindow,
        networkService: INetworkService,
        currentUserConfig: CurrentUserConfig
    ) {
        self.window = window
        self.networkService = networkService
        self.currentUserConfig = currentUserConfig
    }
}

extension RegistrationCoordinator: Coordinatable {

    func start(animated: Bool) {
        let moduleBuilder = RegistrationModuleBuilder(
            output: self,
            networkService: networkService,
            currentUserConfig: currentUserConfig
        )
        rootController = moduleBuilder.build()

        switchViewController(rootController, in: window)
    }

    func finish(animated: Bool, completion: (() -> Void)?) {
        guard !finishHandlers.isEmpty else {
            childCoordinators.finishAll(animated: animated, completion: completion)
            return
        }
        completion.map { finishHandlers.append($0) }
    }
}

// MARK: - RegistrationModuleBuilder

extension RegistrationCoordinator: RegistrationModuleOutput {

    func moduleWantsToGoToTheNextStep(_ module: RegistrationModuleInput) {
        let сoordinator = MainCoordinator(window: window)
        childCoordinators.append(сoordinator)
        сoordinator.start(animated: true)
    }
}
