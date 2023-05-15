//
//  SearchTeamsPageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol SearchTeamsPageViewInput: AnyObject {
    func setupLoadingState()
    func setupDataState(with data: [TeamApplicationDisplayModel])
    func setupErrorState()
    func setupEmptyState()
    func nothingFoundState()
    func showNewApplicationWasCreatedMessage()
    func showNewApplicationWasNotCreatedMessage()
}

protocol SearchTeamsPageViewOutput: AnyObject {
    func viewDidLoad()
    func viewStartRefreshing()
    func searchBarTextDidChange(text: String)
    func viewWantsToOpenCreateApplictaionScreen()
    func viewWantsToOpenFilters()
    func wantsToOpenTeamApplication(team: TeamApplicationDisplayModel)
}
