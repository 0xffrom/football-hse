//
//  MyApplicationsPageModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol MyApplicationsPageModuleInput: AnyObject {
    
}

public protocol MyApplicationsPageModuleOutput: AnyObject {
    func openEditProfile()
    func exit()
    func registerTeam()
    func openMyApplications()
}
