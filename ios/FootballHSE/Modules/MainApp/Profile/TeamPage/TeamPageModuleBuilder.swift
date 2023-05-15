//
//  TeamPageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class TeamPageModuleBuilder {

    // MARK: Private Properties

    private weak var output: TeamPageModuleOutput?
    
    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: TeamPageModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = TeamPageInteractor(
            networkService: networkService
        )
        let presenter = TeamPagePresenter(interactor: interactor)

        let viewController = R.storyboard.teamPage.teamPageViewController()!

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
