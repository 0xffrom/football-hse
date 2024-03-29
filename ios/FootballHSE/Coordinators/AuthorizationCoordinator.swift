//
//  AuthorizationCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.02.2023.
//

import Foundation
import UIKit

final class AuthorizationCoordinator {

    // MARK: Private Properties

    private unowned let window: UIWindow

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private weak var parentNavigationController: UINavigationController?

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(
        parentNavigationController: UINavigationController?,
        window: UIWindow,
        networkService: INetworkService,
        finishHandler: (() -> Void)?
    ) {
        self.parentNavigationController = parentNavigationController
        self.window = window
        self.networkService = networkService
        finishHandlers.append(finishHandler)
    }
}

// MARK: Coordinatable

extension AuthorizationCoordinator: Coordinatable {

    func start(animated: Bool) {
        let builder = AuthorizationPhoneEnteringModuleBuilder(
            output: self,
            networkService: networkService
        )

        let viewController = builder.build()
        let navigationController = UINavigationController(rootViewController: viewController)
        parentNavigationController = navigationController

        switchViewController(parentNavigationController, in: window)
    }

    func finish(animated: Bool, completion: (() -> Void)?) {
        guard !finishHandlers.isEmpty else {
            childCoordinators.finishAll(animated: animated, completion: completion)
            return
        }
        completion.map { finishHandlers.append($0) }
    }
}

// MARK: - AuthorizationPhoneEnteringModuleOutput

extension AuthorizationCoordinator: AuthorizationPhoneEnteringModuleOutput {

    func moduleWantsToGoToTheNextStep(_ module: AuthorizationPhoneEnteringModuleInput) {
        let builder = AuthorizationCodeEnteringModuleBuilder(
            output: self,
            networkService: networkService
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}

// MARK: - AuthorizationCodeEnteringModuleOutput

extension AuthorizationCoordinator: AuthorizationCodeEnteringModuleOutput {

    func moduleWantsToGoToRegistration(_ module: AuthorizationCodeEnteringModuleInput) {
        let сoordinator = RegistrationCoordinator(
            window: window,
            networkService: networkService
        )
        childCoordinators.append(сoordinator)
        сoordinator.start(animated: true)
    }

    func moduleWantsToGoToMainApp(_ module: AuthorizationCodeEnteringModuleInput) {
        let сoordinator = MainCoordinator(
            window: window,
            networkService: networkService
        )
        childCoordinators.append(сoordinator)
        сoordinator.start(animated: true)
    }
}
