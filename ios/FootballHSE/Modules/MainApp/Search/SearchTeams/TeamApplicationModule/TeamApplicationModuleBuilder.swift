//
//  TeamApplicationModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class TeamApplicationModuleBuilder {

    // MARK: Private Properties

    private weak var output: TeamApplicationModuleOutput?
    
    private let networkService: INetworkService

    private let team: TeamApplicationDisplayModel

    // MARK: Lifecycle

    public init(
        output: TeamApplicationModuleOutput?,
        networkService: INetworkService,
        team: TeamApplicationDisplayModel
    ) {
        self.output = output
        self.networkService = networkService
        self.team = team
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = TeamApplicationInteractor(
            networkService: networkService
        )
        let presenter = TeamApplicationPresenter(interactor: interactor)

        let viewController = R.storyboard.teamApplication.teamApplicationViewController()!

        viewController.output = presenter
        viewController.data = team
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
