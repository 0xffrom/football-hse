//
//  MyApplicationsPageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol MyApplicationsPageViewInput: AnyObject {
    func setupLoadingState()
    func setupDataState(with data: [ProfilePlayerApplicationDisplayModel])
    func setupErrorState()
    func setupEmptyState()
}

protocol MyApplicationsPageViewOutput: AnyObject {
    func viewDidLoad()
    func viewStartRefreshing()
}
