//
//  SearchTeamsPagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

final class SearchTeamsPagePresenter {

    // MARK: Public Properties

    weak var view: SearchTeamsPageViewInput?
    var moduleOutput: SearchTeamsPageModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: SearchTeamsPageInteractorInput

    private var nameIsValid: Bool = false
    private var name: String? = nil
    private var selectedRole: PlayerRole? = nil

    // MARK: Lifecycle

    init(interactor: SearchTeamsPageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - SearchTeamsPageModuleInput

extension SearchTeamsPagePresenter: SearchTeamsPageModuleInput {}

// MARK: - SearchTeamsPageInteractorOutput

extension SearchTeamsPagePresenter: SearchTeamsPageInteractorOutput {}

// MARK: - SearchTeamsPageViewOutput

extension SearchTeamsPagePresenter: SearchTeamsPageViewOutput {
    
}
