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

    // MARK: Lifecycle

    public init(
        output: AuthorizationCodeEnteringModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = AuthorizationCodeEnteringInteractor(
            networkService: networkService
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
