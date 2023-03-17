//
//  RegistrationModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class RegistrationModuleBuilder {

    // MARK: Private Properties

    private weak var output: RegistrationModuleOutput?

    private let networkService: INetworkService
    private let currentUserConfig: CurrentUserConfig

    // MARK: Lifecycle

    public init(
        output: RegistrationModuleOutput?,
        networkService: INetworkService,
        currentUserConfig: CurrentUserConfig
    ) {
        self.output = output
        self.networkService = networkService
        self.currentUserConfig = currentUserConfig
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = RegistrationInteractor(
            networkService: networkService,
            currentUserConfig: currentUserConfig
        )
        let presenter = RegistrationPresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "Registration", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "RegistrationViewController") as! RegistrationViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
