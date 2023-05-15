//
//  TeamPageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol TeamPageViewInput: AnyObject {
    func setLoadingState()
    func removeLoadingState()
    func showAlert()
}

protocol TeamPageViewOutput: AnyObject {
    func deleteTeam()
    func openTeamApplications()
}
