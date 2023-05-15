//
//  SearchTeamsPlayerRoleFilterModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class SearchTeamsPlayerRoleFilterModuleBuilder<T: Filterabale> {

    // MARK: Private Properties

    private weak var output: SearchTeamsPlayerRoleFilterModuleOutput?

    private let networkService: INetworkService
    private let searchByFiltersService: SearchByFiltersService

    // MARK: Lifecycle

    public init(
        output: SearchTeamsPlayerRoleFilterModuleOutput?,
        networkService: INetworkService,
        searchByFiltersService: SearchByFiltersService
    ) {
        self.output = output
        self.networkService = networkService
        self.searchByFiltersService = searchByFiltersService
    }

    // MARK: Public Methods

    public func build(withTitle title: String) -> UIViewController {
        let interactor = SearchTeamsPlayerRoleFilterInteractor(
            networkService: networkService
        )
        let presenter = SearchTeamsPlayerRoleFilterPresenter<T>(
            interactor: interactor,
            searchByFiltersService: searchByFiltersService
        )

        let storyboard = UIStoryboard(name: "SearchTeamsPlayerRoleFilter", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "SearchTeamsPlayerRoleFilterViewController") as! SearchTeamsPlayerRoleFilterViewController

        viewController.output = presenter
        viewController.title = title
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
