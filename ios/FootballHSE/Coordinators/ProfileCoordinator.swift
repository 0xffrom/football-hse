//
//  ProfileCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 12.03.2023.
//

import Foundation
import UIKit

final class ProfileCoordinator {

    // MARK: Private Properties

    private unowned let window: UIWindow

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private weak var parentTabBarController: UITabBarController?
    private weak var parentNavigationController: UINavigationController?

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(
        parentTabBarController: UITabBarController,
        networkService: INetworkService,
        window: UIWindow
    ) {
        self.parentTabBarController = parentTabBarController
        self.networkService = networkService
        self.window = window
    }
}

// MARK: Coordinatable

extension ProfileCoordinator: Coordinatable {

    func start(animated: Bool) {
        let builder = ProfilePageModuleBuilder(output: self)
        let viewController = builder.build()

        let navigationController = UINavigationController(rootViewController: viewController)
        parentNavigationController = navigationController

        parentTabBarController?.addViewController(parentNavigationController!)
    }

    func finish(animated: Bool, completion: (() -> Void)?) {
        guard !finishHandlers.isEmpty else {
            childCoordinators.finishAll(animated: animated, completion: completion)
            return
        }
        completion.map { finishHandlers.append($0) }
    }
}

extension ProfileCoordinator: ProfilePageModuleOutput {

    func registerTeam() {
        let builder = RegisterTeamPageModuleBuilder(
            output: self,
            networkService: networkService
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func openEditProfile() {
        let builder = EditProfilePageModuleBuilder(
            output: self,
            networkService: networkService
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func openMyApplications() {
        let builder = MyApplicationsPageModuleBuilder(
            output: self,
            networkService: networkService
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func exit() {
        CurrentUserConfig.clear()

        let rootCoordinator = RootCoordinator(
            parentNavigationController: UINavigationController(),
            window: window,
            finishHandler: nil
        )
        childCoordinators.append(rootCoordinator)
        rootCoordinator.start(animated: true)
    }
}

extension ProfileCoordinator: EditProfilePageModuleOutput {

    func back() {
        parentNavigationController?.popViewController(animated: true)
    }
}

extension ProfileCoordinator: RegisterTeamPageModuleOutput {}

extension ProfileCoordinator: MyApplicationsPageModuleOutput {}
