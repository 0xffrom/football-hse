//
//  ProfilePageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class ProfilePageModuleBuilder {

    // MARK: Private Properties

    private weak var output: ProfilePageModuleOutput?

    // MARK: Lifecycle

    public init(output: ProfilePageModuleOutput?) {
        self.output = output
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = ProfilePageInteractor()
        let presenter = ProfilePagePresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "ProfilePage", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "ProfilePageViewController") as! ProfilePageViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
