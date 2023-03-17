//
//  AuthorizationCodeEnteringModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class AuthorizationCodeEnteringModuleBuilder {

    // MARK: Private Properties

    private weak var output: AuthorizationCodeEnteringModuleOutput?

    private let networkService: INetworkService
    private let currentUserConfig: CurrentUserConfig

    // MARK: Lifecycle

    public init(
        output: AuthorizationCodeEnteringModuleOutput?,
        networkService: INetworkService,
        currentUserConfig: CurrentUserConfig
    ) {
        self.output = output
        self.networkService = networkService
        self.currentUserConfig = currentUserConfig
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = AuthorizationCodeEnteringInteractor(
            networkService: networkService,
            currentUserConfig: currentUserConfig
        )
        let presenter = AuthorizationCodeEnteringPresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "AuthorizationCodeEntering", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "AuthorizationCodeEnteringViewController") as! AuthorizationCodeEnteringViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
