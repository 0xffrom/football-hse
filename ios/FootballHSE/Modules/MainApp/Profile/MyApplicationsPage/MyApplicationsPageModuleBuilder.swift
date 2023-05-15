//
//  MyApplicationsPageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public enum TypeOfApplications {
    case user
    case team
}

public final class MyApplicationsPageModuleBuilder {

    // MARK: Private Properties

    private weak var output: MyApplicationsPageModuleOutput?
    private let networkService: INetworkService
    private let type: TypeOfApplications

    // MARK: Lifecycle

    public init(
        output: MyApplicationsPageModuleOutput?,
        networkService: INetworkService,
        type: TypeOfApplications
    ) {
        self.output = output
        self.networkService = networkService
        self.type = type
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = MyApplicationsPageInteractor(networkService: networkService, type: type)
        let presenter = MyApplicationsPagePresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "MyApplicationsPage", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "MyApplicationsPageViewController") as! MyApplicationsPageViewController

        viewController.output = presenter
        viewController.type = type
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
