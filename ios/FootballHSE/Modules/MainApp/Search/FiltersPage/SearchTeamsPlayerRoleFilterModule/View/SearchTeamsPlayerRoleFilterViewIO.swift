//
//  SearchTeamsPlayerRoleFilterViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol SearchTeamsPlayerRoleFilterViewInput: AnyObject {
    func setupDataState(with data: SearchTeamsPlayerRoleFilterViewController.DisplayData)
    func showAlertMustChooseAllFilters()
}

protocol SearchTeamsPlayerRoleFilterViewOutput: AnyObject {
    func viewDidLoad()
    func saveSelections()
    func removeSelection()
}
