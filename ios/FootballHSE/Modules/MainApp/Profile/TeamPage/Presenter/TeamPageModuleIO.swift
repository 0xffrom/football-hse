//
//  TeamPageModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol TeamPageModuleInput: AnyObject {
    
}

public protocol TeamPageModuleOutput: AnyObject {
    func backWithTeamDeleted()
    func openTeamApplications()
}
