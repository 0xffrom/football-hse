//
//  SearchTeamsPlayerRoleFilterPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class SearchTeamsPlayerRoleFilterPresenter {

    // MARK: Public Properties

    weak var view: SearchTeamsPlayerRoleFilterViewInput?
    weak var moduleOutput: SearchTeamsPlayerRoleFilterModuleOutput?

    // MARK: Private Properties

    private let interactor: SearchTeamsPlayerRoleFilterInteractorInput

    // MARK: Lifecycle

    init(interactor: SearchTeamsPlayerRoleFilterInteractorInput) {
        self.interactor = interactor
    }
}

// MARK: - SearchTeamsPlayerRoleFilterModuleInput

extension SearchTeamsPlayerRoleFilterPresenter: SearchTeamsPlayerRoleFilterModuleInput {}

// MARK: - SearchTeamsPlayerRoleFilterInteractorOutput

extension SearchTeamsPlayerRoleFilterPresenter: SearchTeamsPlayerRoleFilterInteractorOutput {}

// MARK: - SearchTeamsPlayerRoleFilterViewOutput

extension SearchTeamsPlayerRoleFilterPresenter: SearchTeamsPlayerRoleFilterViewOutput {}
