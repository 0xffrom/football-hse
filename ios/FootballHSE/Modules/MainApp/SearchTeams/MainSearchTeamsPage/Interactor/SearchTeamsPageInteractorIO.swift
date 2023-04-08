//
//  SearchTeamsPageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol SearchTeamsPageInteractorInput: AnyObject {
    func getSearchResults(completion: @escaping (Result<[TeamApplicationDisplayModel], Error>) -> Void)
}

protocol SearchTeamsPageInteractorOutput: AnyObject {
    
}