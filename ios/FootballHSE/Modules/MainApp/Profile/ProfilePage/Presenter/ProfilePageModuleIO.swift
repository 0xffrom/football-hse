//
//  ProfilePageModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol ProfilePageModuleInput: AnyObject {
    func updateProfileInfo()
    func setupTeamRegistrationInProgressView()
    func setupRegisterTeamView() 
}

public protocol ProfilePageModuleOutput: AnyObject {
    func moduleDidLoad(_ module: ProfilePageModuleInput)
    func openEditProfile()
    func exit()
    func registerTeam()
    func openMyApplications()
    func openTeamInfo()
}
