//
//  SearchPlayersPageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class SearchPlayersPageModuleBuilder {

    // MARK: Private Properties

    private weak var output: SearchPlayersPageModuleOutput?
    
    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: SearchPlayersPageModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = SearchPlayersPageInteractor(
            networkService: networkService
        )
        let presenter = SearchPlayersPagePresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "SearchPlayersPage", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "SearchPlayersPageViewController") as! SearchPlayersPageViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
