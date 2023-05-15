//
//  SearchPlayersCoordinator.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 12.03.2023.
//

import Foundation
import UIKit

final class SearchPlayersCoordinator {

    enum ChildPage {
        case filters
        case creationOfApplication
    }

    // MARK: Private Properties

    private var childCoordinators: [Coordinatable] = []
    private var finishHandlers: [(() -> Void)?] = []

    private unowned let window: UIWindow

    private weak var parentTabBarController: UITabBarController?
    private weak var parentNavigationController: UINavigationController?

    private weak var input: SearchPlayersPageModuleInput?

    private let networkService: INetworkService

    private let searchByFiltersService = SearchByFiltersService(
        numberOfFilters: 2,
        fltersNames: ["Позиции", "Турниры"],
        mustChooseAllFilters: false
    )
    private let createApplicationFiltersService = SearchByFiltersService(
        numberOfFilters: 2,
        fltersNames: ["Позиции", "Турниры"],
        mustChooseAllFilters: true
    )
    private var title: String?
    private var childPageType: ChildPage?

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

extension SearchPlayersCoordinator: Coordinatable {

    func start(animated: Bool) {
        let builder = SearchPlayersPageModuleBuilder(
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

extension SearchPlayersCoordinator: SearchPlayersPageModuleOutput {

    func moduleDidLoad(_ module: SearchPlayersPageModuleInput) {
        input = module
    }

    func openCreateApplictaionScreen() {
        title = "Создание заявки"
        childPageType = .creationOfApplication

        let builder = SearchTeamsPlayerRoleFilterModuleBuilder<PlayerPosition>(
            output: self,
            networkService: networkService,
            searchByFiltersService: createApplicationFiltersService
        )
        let viewController = builder.build(withTitle: title ?? "")
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func openPlayersApplication(player: PlayersApplicationDisplayModel, playerImageURL: String?) {
        let builder = PlayersApplicationModuleBuilder(
            output: self,
            networkService: networkService,
            player: player,
            playerImageURL: playerImageURL
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }

    func openFilters() {
        title = "Выбор фильтров"
        childPageType = .filters

        let builder = SearchTeamsPlayerRoleFilterModuleBuilder<PlayerPosition>(
            output: self,
            networkService: networkService,
            searchByFiltersService: searchByFiltersService
        )
        let viewController = builder.build(withTitle: title ?? "")
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}

extension SearchPlayersCoordinator: PlayersApplicationModuleOutput {

    func back() {
        parentNavigationController?.popViewController(animated: true)
    }

    func wantsToOpenConversation(phoneNumber: String?, name: String?, image: UIImage?, interlocutorsImageURL: String?, conversationID: Int, lastMessage: MessageModel?) {
        let builder = ConversationModuleBuilder(
            output: self,
            networkService: networkService,
            interlocutorsPhoneNamber: phoneNumber ?? "",
            interlocutorsName: name,
            interlocutorsImageURL: interlocutorsImageURL,
            conversationID: conversationID,
            image: image,
            lastMessage: lastMessage
        )
        let viewController = builder.build()
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}

extension SearchPlayersCoordinator: ConversationModuleOutput {}

extension SearchPlayersCoordinator: SearchTeamsPlayerRoleFilterModuleOutput {

    func showResults(filters: [Int]) {
        guard let childPageType else { return }
        switch childPageType {
        case .filters:
            input?.applyFilters(position: filters[0], tournaments: filters[1])
        case .creationOfApplication:
            input?.createApplication(position: filters[0], tournaments: filters[1])
        }

        parentNavigationController?.popToRootViewController(animated: true)
    }

    func openNextFilter() {
        var service: SearchByFiltersService?

        guard let childPageType else { return }
        switch childPageType {
        case .filters:
            service = self.searchByFiltersService
        case .creationOfApplication:
            service = self.createApplicationFiltersService
        }

        let builder = SearchTeamsPlayerRoleFilterModuleBuilder<Tournament>(
            output: self,
            networkService: networkService,
            searchByFiltersService: service!
        )
        let viewController = builder.build(withTitle: title ?? "")
        parentNavigationController?.pushViewController(viewController, animated: true)
    }
}
