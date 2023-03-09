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

    // MARK: Lifecycle

    public init(output: RegistrationModuleOutput?) {
        self.output = output
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = RegistrationInteractor()
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
