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

    // MARK: Lifecycle

    public init(
        output: AuthorizationPhoneEnteringModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = AuthorizationPhoneEnteringInteractor(
            networkService: networkService
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
