//
//  MainModuleBuilder.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 11.03.2023.
//

import UIKit

public final class MainModuleBuilder {

    // MARK: Private Properties

    private weak var output: MainModuleOutput?

    // MARK: Lifecycle

    public init(output: MainModuleOutput?) {
        self.output = output
    }

    // MARK: Public Methods

    public func build() -> UITabBarController {
        let presenter = MainPresenter()

        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "MainViewController") as! MainViewController

        viewController.output = presenter

        presenter.view = viewController
        presenter.moduleOutput = output

        return viewController
    }
}
