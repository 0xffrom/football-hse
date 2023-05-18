//
//  MainPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

final class MainPresenter {

    // MARK: Public Properties

    weak var view: MainViewInput?
    var moduleOutput: MainModuleOutput?
}

// MARK: - MainModuleInput

extension MainPresenter: MainModuleInput {}

// MARK: - MainViewOutput

extension MainPresenter: MainViewOutput {}
