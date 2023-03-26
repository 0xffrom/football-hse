//
//  SearchTeamsPageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class SearchTeamsPageModuleBuilder {

    // MARK: Private Properties

    private weak var output: SearchTeamsPageModuleOutput?
    
    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: SearchTeamsPageModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = SearchTeamsPageInteractor(
            networkService: networkService
        )
        let presenter = SearchTeamsPagePresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "SearchTeamsPage", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "SearchTeamsPageViewController") as! SearchTeamsPageViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
