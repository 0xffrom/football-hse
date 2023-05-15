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

    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(output: ProfilePageModuleOutput?,
                networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = ProfilePageInteractor(networkService: networkService)
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
