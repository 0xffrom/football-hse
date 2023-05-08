//
//  SearchPlayersPageModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol SearchPlayersPageModuleInput: AnyObject {
    func applyFilters(position: Int, tournaments: Int)
    func createApplication(position: Int, tournaments: Int)
}

public protocol SearchPlayersPageModuleOutput: AnyObject {
    func moduleDidLoad(_ module: SearchPlayersPageModuleInput)
    func openCreateApplictaionScreen()
    func openFilters()
    func openPlayersApplication(player: PlayersApplicationDisplayModel)
}

