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

    private var resignCapitanStatusAction: (() -> Void)?

    private let networkService: INetworkService

    private weak var input: ProfilePageModuleInput?

    // MARK: Lifecycle

    init(
        parentTabBarController: UITabBarController,
        networkService: INetworkService,
        window: UIWindow,
        resignCapitanStatusAction: (() -> Void)?
    ) {
        self.parentTabBarController = parentTabBarController
        self.networkService = networkService
        self.window = window
        self.resignCapitanStatusAction = resignCapitanStatusAction
    }
}

// MARK: Coordinatable

extension ProfileCoordinator: Coordinatable {

    func start(animated: Bool) {
        let builder = ProfilePageModuleBuilder(output: self, networkService: networkService)
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

    func moduleDidLoad(_ module: ProfilePageModuleInput) {
        input = module
    }

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
            networkService: networkService,
            type: .user
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func openTeamInfo() {
        let builder = TeamPageModuleBuilder(
            output: self,
            networkService: networkService
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func exit() {
        CurrentUserConfig.clear()
        CurrentTeamConfig.clear()

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
        input?.updateProfileInfo()
        parentNavigationController?.popViewController(animated: true)
    }
}

extension ProfileCoordinator: RegisterTeamPageModuleOutput {

    func backWithTeamRegistered() {
        input?.setupTeamRegistrationInProgressView()
        parentNavigationController?.popViewController(animated: true)
    }
}

extension ProfileCoordinator: MyApplicationsPageModuleOutput {}

extension ProfileCoordinator: TeamPageModuleOutput {

    func backWithTeamDeleted() {
        input?.setupRegisterTeamView()
        resignCapitanStatusAction?()
        parentNavigationController?.popViewController(animated: true)
    }

    func openTeamApplications() {
        let builder = MyApplicationsPageModuleBuilder(
            output: self,
            networkService: networkService,
            type: .team
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}
