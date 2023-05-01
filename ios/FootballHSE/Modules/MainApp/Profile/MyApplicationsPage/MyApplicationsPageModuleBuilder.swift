//
//  MyApplicationsPageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class MyApplicationsPageModuleBuilder {

    // MARK: Private Properties

    private weak var output: MyApplicationsPageModuleOutput?
    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: MyApplicationsPageModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = MyApplicationsPageInteractor(networkService: networkService)
        let presenter = MyApplicationsPagePresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "MyApplicationsPage", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "MyApplicationsPageViewController") as! MyApplicationsPageViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
