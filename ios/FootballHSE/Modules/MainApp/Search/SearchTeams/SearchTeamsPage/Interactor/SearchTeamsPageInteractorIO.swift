//
//  SearchTeamsPageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol SearchTeamsPageInteractorInput: AnyObject {
    func getSearchResults(completion: @escaping (Result<[TeamApplicationDisplayModel], Error>) -> Void)
    func getSearchResultsWithFilters(position: Int, tournaments: Int,
                                     completion: @escaping (Result<[TeamApplicationDisplayModel], Error>) -> Void)
    func createNewApplication(position: Int, tournaments: Int,
                                     completion: @escaping (Result<Void, Error>) -> Void)
    func getImageURLForTeam(with id: Int) -> String?
}

protocol SearchTeamsPageInteractorOutput: AnyObject {
    
}
