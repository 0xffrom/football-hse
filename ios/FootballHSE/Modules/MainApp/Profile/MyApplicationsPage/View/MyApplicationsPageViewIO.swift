//
//  MyApplicationsPageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol MyApplicationsPageViewInput: AnyObject {
    func setupLoadingState()
    func setupDataState(with data: [ProfileApplicationDisplayModel])
    func setupErrorState()
    func setupEmptyState()
    func setupErrorWhileDeletingState()
}

protocol MyApplicationsPageViewOutput: AnyObject {
    func viewDidLoad()
    func viewStartRefreshing()
    func deleteApplication(with id: Int)
}
