//
//  RegisterTeamPageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class RegisterTeamPageModuleBuilder {

    // MARK: Private Properties

    private weak var output: RegisterTeamPageModuleOutput?

    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: RegisterTeamPageModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = RegisterTeamPageInteractor()
        let presenter = RegisterTeamPagePresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "RegisterTeamPage", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "RegisterTeamPageViewController") as! RegisterTeamPageViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
