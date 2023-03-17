//
//  SearchTeamsCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 12.03.2023.
//

import Foundation
import UIKit

final class SearchTeamsCoordinator {

    // MARK: Private Properties

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private weak var parentTabBarController: UITabBarController?

    // MARK: Lifecycle

    init(
        parentTabBarController: UITabBarController
    ) {
        self.parentTabBarController = parentTabBarController
    }
}

// MARK: Coordinatable

extension SearchTeamsCoordinator: Coordinatable {

    func start(animated: Bool) {
        let builder = SearchTeamsPageModuleBuilder(output: self)
        let viewController = builder.build()

        let navigationController = UINavigationController(rootViewController: viewController)

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

extension SearchTeamsCoordinator: SearchTeamsPageModuleOutput {

    func moduleWantsToGoToTheNextStep(_ module: SearchTeamsPageModuleInput) {
        //
    }
}
