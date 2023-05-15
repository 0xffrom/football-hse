//
//  RegisterTeamPageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

protocol RegisterTeamPageInteractorInput: AnyObject {
    func createTeam(with teamInfo: CreateTeamModel, image: UIImage?, completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol RegisterTeamPageInteractorOutput: AnyObject {
    
}
