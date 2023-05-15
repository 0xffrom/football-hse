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

    // MARK: Lifecycle

    public init(
        output: RegistrationModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = RegistrationInteractor(
            networkService: networkService
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
