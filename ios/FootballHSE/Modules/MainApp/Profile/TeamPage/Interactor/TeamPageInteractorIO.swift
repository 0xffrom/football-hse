//
//  TeamPageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol TeamPageInteractorInput: AnyObject {
    func deleteTeam(completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol TeamPageInteractorOutput: AnyObject {
    
}
