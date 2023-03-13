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

extension AuthorizationCoordinator: Coordinatable {

    func start(animated: Bool) {
        guard let parentNavigationController = parentNavigationController else { return }

        let builder = AuthorizationPhoneEnteringModuleBuilder(output: self)

        let viewController = builder.build()
        rootViewController = viewController

        parentNavigationController.pushViewController(viewController, animated: animated)
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
        let builder = AuthorizationCodeEnteringModuleBuilder(output: self)
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}

// MARK: - AuthorizationCodeEnteringModuleOutput

extension AuthorizationCoordinator: AuthorizationCodeEnteringModuleOutput {

    func moduleWantsToGoToTheNextStep(_ module: AuthorizationCodeEnteringModuleInput) {
        let builder = RegistrationModuleBuilder(output: self)
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}


// MARK: - RegistrationModuleBuilder

extension AuthorizationCoordinator: RegistrationModuleOutput {

    func moduleWantsToGoToTheNextStep(_ module: RegistrationModuleInput) {
//        let builder = RegistrationModuleBuilder(output: self)
//        let viewController = builder.build()
//        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}
