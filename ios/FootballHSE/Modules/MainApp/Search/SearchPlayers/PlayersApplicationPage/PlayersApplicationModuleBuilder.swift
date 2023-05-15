//
//  PlayersApplicationModuleBuilder.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

public final class PlayersApplicationModuleBuilder {

    // MARK: Private Properties

    private weak var output: PlayersApplicationModuleOutput?
    
    private let networkService: INetworkService

    private let player: PlayersApplicationDisplayModel
    private let playerImageURL: String?

    // MARK: Lifecycle

    public init(
        output: PlayersApplicationModuleOutput?,
        networkService: INetworkService,
        player: PlayersApplicationDisplayModel,
        playerImageURL: String?
    ) {
        self.output = output
        self.networkService = networkService
        self.player = player
        self.playerImageURL = playerImageURL
    }

    // MARK: Public Methods

    public func build() -> UIViewController {
        let interactor = PlayersApplicationInteractor(
            networkService: networkService,
            phoneNumber: player.playerPhoneNumber,
            name: player.name,
            image: playerImageURL
        )

        let presenter = PlayersApplicationPresenter(
            interactor: interactor,
            phoneNumber: player.playerPhoneNumber,
            name: player.name,
            image: player.photo
        )

        let viewController = R.storyboard.playersApplication.playersApplicationViewController()!

        viewController.output = presenter
        viewController.data = player
        
        presenter.view = viewController
        presenter.moduleOutput = output

        interactor.output = presenter

        return viewController
    }
}
