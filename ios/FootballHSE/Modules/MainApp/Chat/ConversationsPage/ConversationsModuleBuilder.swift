//
//  ConversationsModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class ConversationsModuleBuilder {

    // MARK: Private Properties

    private weak var output: ConversationsModuleOutput?
    
    private let networkService: INetworkService

    // MARK: Lifecycle

    public init(
        output: ConversationsModuleOutput?,
        networkService: INetworkService
    ) {
        self.output = output
        self.networkService = networkService
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = ConversationsInteractor(
            networkService: networkService
        )
        let presenter = ConversationsPresenter(interactor: interactor)

        let storyboard = UIStoryboard(name: "Conversations", bundle: nil)
        let viewController = storyboard.instantiateViewController(withIdentifier: "ConversationsViewController") as! ConversationsViewController

        viewController.output = presenter
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
