//
//  AuthorizationPhoneEnteringModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class AuthorizationPhoneEnteringModuleBuilder {

    // MARK: Private Properties

    private weak var output: AuthorizationPhoneEnteringModuleOutput?

    private let networkService: INetworkService
    private let currentUserConfig: CurrentUserConfig

    // MARK: Lifecycle

    public init(
        output: AuthorizationPhoneEnteringModuleOutput?,
        networkService: INetworkService,
        currentUserConfig: CurrentUserConfig
    ) {
        self.output = output
        self.networkService = networkService
        self.currentUserConfig = currentUserConfig
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = AuthorizationPhoneEnteringInteractor(
            networkService: networkService,
            currentUserConfig: currentUserConfig
        )
        let presenter = AuthorizationPhoneEnteringPresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "AuthorizationPhoneEntering", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "AuthorizationPhoneEnteringViewController") as! AuthorizationPhoneEnteringViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
