//
//  SearchTeamsPlayerRoleFilterModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class SearchTeamsPlayerRoleFilterModuleBuilder {

    // MARK: Private Properties

    private weak var output: SearchTeamsPlayerRoleFilterModuleOutput?

    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: SearchTeamsPlayerRoleFilterModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = SearchTeamsPlayerRoleFilterInteractor(
            networkService: networkService
        )
        let presenter = SearchTeamsPlayerRoleFilterPresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "SearchTeamsPlayerRoleFilter", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "SearchTeamsPlayerRoleFilterViewController") as! SearchTeamsPlayerRoleFilterViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
