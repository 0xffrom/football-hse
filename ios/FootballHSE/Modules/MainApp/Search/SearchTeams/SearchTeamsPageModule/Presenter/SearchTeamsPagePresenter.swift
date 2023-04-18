//
//  SearchTeamsPagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class SearchTeamsPagePresenter {

    // MARK: Public Properties

    weak var view: SearchTeamsPageViewInput?
    weak var moduleOutput: SearchTeamsPageModuleOutput?
    
    // MARK: Private Properties

    private let interactor: SearchTeamsPageInteractorInput

    private var aplications: [TeamApplicationDisplayModel] = []

    // MARK: Lifecycle

    init(interactor: SearchTeamsPageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private

    private func getSerachResults() {
        interactor.getSearchResults { [weak self] result in
            switch result {
            case .success(let data):
                self?.aplications = data
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    if data.isEmpty {
                        view.setupEmptyState()
                    } else {
                        view.setupDataState(with: data)
                    }
                }
            case .failure(_):
                self?.aplications = []
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.setupErrorState()
                }
            }
        }
    }
}

// MARK: - SearchTeamsPageModuleInput

extension SearchTeamsPagePresenter: SearchTeamsPageModuleInput {}

// MARK: - SearchTeamsPageInteractorOutput

extension SearchTeamsPagePresenter: SearchTeamsPageInteractorOutput {}

// MARK: - SearchTeamsPageViewOutput

extension SearchTeamsPagePresenter: SearchTeamsPageViewOutput {

    func viewDidLoad() {
        view?.setupLoadingState()
        getSerachResults()
    }

    func viewStartRefreshing() {
        getSerachResults()
    }

    func searchBarTextDidChange(text: String) {
        let filteredAplications = text.isEmpty
        ? aplications
        : aplications.filter { (item: TeamApplicationDisplayModel) -> Bool in
            item.name.range(of: text, options: .caseInsensitive, range: nil, locale: nil) != nil
        }

        DispatchQueue.main.async { [weak self] in
            guard let self = self, let view = self.view else { return }
            if filteredAplications.isEmpty {
                view.nothingFoundState()
            } else {
                view.setupDataState(with: filteredAplications)
            }
        }
    }

    func viewWantsToOpenCreateApplictaionScreen() {
        moduleOutput?.openCreateApplictaionScreen()
    }

    func viewWantsToOpenFilters() {
        moduleOutput?.openFilters()
    }

    func wantsToOpenTeamApplication(team: TeamApplicationDisplayModel) {
        moduleOutput?.openTeamApplication(team: team)
    }
}
