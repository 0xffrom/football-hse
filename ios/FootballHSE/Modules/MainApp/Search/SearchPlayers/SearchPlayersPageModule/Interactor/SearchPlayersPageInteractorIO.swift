//
//  SearchPlayersPageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol SearchPlayersPageInteractorInput: AnyObject {
    func getSearchResults(completion: @escaping (Result<[PlayersApplicationDisplayModel], Error>) -> Void)
    func getSearchResultsWithFilters(position: Int, tournaments: Int,
                                     completion: @escaping (Result<[PlayersApplicationDisplayModel], Error>) -> Void)
    func createNewApplication(position: Int, tournaments: Int,
                                     completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol SearchPlayersPageInteractorOutput: AnyObject {}
