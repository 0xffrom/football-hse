//
//  SearchTeamsPageModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol SearchTeamsPageModuleInput: AnyObject {
    func applyFilters(position: Int, tournaments: Int)
    func createApplication(position: Int, tournaments: Int)
}

public protocol SearchTeamsPageModuleOutput: AnyObject {
    func moduleDidLoad(_ module: SearchTeamsPageModuleInput)
    func openCreateApplictaionScreen()
    func openFilters()
    func openTeamApplication(team: TeamApplicationDisplayModel, teamImageURL: String?)
}

