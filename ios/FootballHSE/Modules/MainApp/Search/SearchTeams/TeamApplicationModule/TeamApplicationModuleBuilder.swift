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
    private let teamImageURL: String?

    // MARK: Lifecycle

    public init(
        output: TeamApplicationModuleOutput?,
        networkService: INetworkService,
        team: TeamApplicationDisplayModel,
        teamImageURL: String?
    ) {
        self.output = output
        self.networkService = networkService
        self.team = team
        self.teamImageURL = teamImageURL
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = TeamApplicationInteractor(
            networkService: networkService,
            phoneNumber: team.contact,
            name: team.name,
            image: teamImageURL
        )
        let presenter = TeamApplicationPresenter(
            interactor: interactor,
            phoneNumber: team.contact,
            name: team.name,
            image: team.logo
        )

        let viewController = R.storyboard.teamApplication.teamApplicationViewController()!

        viewController.output = presenter
        viewController.data = team
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
