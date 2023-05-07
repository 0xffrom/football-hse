//
//  ProfilePageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol ProfilePageInteractorInput: AnyObject {
    func getTeamInfo(completion: @escaping (Result<TeamModel?, Error>) -> Void)
    func deleteTeam(with: Int)
}

protocol ProfilePageInteractorOutput: AnyObject {
    
}
