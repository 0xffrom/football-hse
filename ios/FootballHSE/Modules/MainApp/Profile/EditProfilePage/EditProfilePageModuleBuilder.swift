//
//  EditProfilePageModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class EditProfilePageModuleBuilder {

    // MARK: Private Properties

    private weak var output: EditProfilePageModuleOutput?

    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: EditProfilePageModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = EditProfilePageInteractor()
        let presenter = EditProfilePagePresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "EditProfilePage", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "EditProfilePageViewController") as! EditProfilePageViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
