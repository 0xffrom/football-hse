//
//  SearchPlayersPageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol SearchPlayersPageViewInput: AnyObject {
    func setupLoadingState()
    func setupDataState(with data: [PlayersApplicationDisplayModel])
    func setupErrorState()
    func setupEmptyState()
    func nothingFoundState()
    func showNewApplicationWasCreatedMessage()
    func showNewApplicationWasNotCreatedMessage()
}

protocol SearchPlayersPageViewOutput: AnyObject {
    func viewDidLoad()
    func viewStartRefreshing()
    func searchBarTextDidChange(text: String)
    func viewWantsToOpenCreateApplictaionScreen()
    func viewWantsToOpenFilters()
    func wantsToOpenPlayersApplication(player: PlayersApplicationDisplayModel)
}
