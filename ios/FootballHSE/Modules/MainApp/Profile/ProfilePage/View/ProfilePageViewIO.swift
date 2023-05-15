//
//  ProfilePageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol ProfilePageViewInput: AnyObject {
    func setupLoadingState()
    func setupRegisterTeamView()
    func removeCaptanStatusView()
    func showTeamDeclineAlert()
    func setupTeamRegistrationInProgressView()
    func setupTeamIsRegisteredView(nameOfTeam: String?)
    func setupErrorWhileLoadingTeamView()
    func updateInfo()
}

protocol ProfilePageViewOutput: AnyObject {
    func viewDidLoad()
    func viewWantToRefreshTeamInfo()
    func viewWantToEditProfile()
    func exit()
    func registerTeam()
    func openMyApplications()
    func deleteDeclinedTeam()
    func openTeamInfo()
}
