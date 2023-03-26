//
//  CreateTeamSearchApplicationModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class CreateTeamSearchApplicationModuleBuilder {

    // MARK: Private Properties

    private weak var output: CreateTeamSearchApplicationModuleOutput?
    
    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: CreateTeamSearchApplicationModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = CreateTeamSearchApplicationInteractor(
            networkService: networkService
        )
        let presenter = CreateTeamSearchApplicationPresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "CreateTeamSearchApplication", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "CreateTeamSearchApplicationViewController") as! CreateTeamSearchApplicationViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
