//
//  SearchTeamsPlayerRoleFilterModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol SearchTeamsPlayerRoleFilterModuleInput: AnyObject {
    
}

public protocol SearchTeamsPlayerRoleFilterModuleOutput: AnyObject {
    func openNextFilter()
    func showResults(filters: [Int])
}
