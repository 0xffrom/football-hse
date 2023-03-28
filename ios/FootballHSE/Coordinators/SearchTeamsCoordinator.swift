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

    private unowned let window: UIWindow

    private weak var parentTabBarController: UITabBarController?
    private weak var parentNavigationController: UINavigationController?

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

extension SearchTeamsCoordinator: Coordinatable {

    func start(animated: Bool) {
        let builder = SearchTeamsPageModuleBuilder(
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

extension SearchTeamsCoordinator: SearchTeamsPageModuleOutput {

    func openCreateApplictaionScreen() {
        let builder = CreateTeamSearchApplicationModuleBuilder(
            output: self,
            networkService: networkService
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func openTeamApplication(team: TeamApplicationDisplayModel) {
        let builder = TeamApplicationModuleBuilder(
            output: self,
            networkService: networkService,
            team: team
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}

extension SearchTeamsCoordinator: CreateTeamSearchApplicationModuleOutput {

    func back() {
        parentNavigationController?.popViewController(animated: true)
    }
}

extension SearchTeamsCoordinator: TeamApplicationModuleOutput {

}
