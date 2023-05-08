//
//  SearchPlayersPagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class SearchPlayersPagePresenter {

    // MARK: Public Properties

    weak var view: SearchPlayersPageViewInput?
    weak var moduleOutput: SearchPlayersPageModuleOutput?
    
    // MARK: Private Properties

    private let interactor: SearchPlayersPageInteractorInput

    private var aplications: [PlayersApplicationDisplayModel] = []

    // MARK: Lifecycle

    init(interactor: SearchPlayersPageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private

    private func handleAplicationsRequest(_ result: Result<[PlayersApplicationDisplayModel], Error>) {
        switch result {
        case .success(let data):
            aplications = data
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                if data.isEmpty {
                    view.setupEmptyState()
                } else {
                    view.setupDataState(with: data)
                }
            }
        case .failure(_):
            aplications = []
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                view.setupErrorState()
            }
        }
    }

    private func getSerachResults() {
        interactor.getSearchResults { [weak self] result in
            self?.handleAplicationsRequest(result)
        }
    }
}

// MARK: - SearchPlayersPageModuleInput

extension SearchPlayersPagePresenter: SearchPlayersPageModuleInput {

    func createApplication(position: Int, tournaments: Int) {
        interactor.createNewApplication(position: position, tournaments: tournaments) { [weak self] result in
            switch result {
            case .success(_):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.showNewApplicationWasCreatedMessage()
                }
            case .failure(_):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.showNewApplicationWasNotCreatedMessage()
                }
            }
        }
    }

    func applyFilters(position: Int, tournaments: Int) {
        interactor.getSearchResultsWithFilters(position: position, tournaments: tournaments) { [weak self] result in
            self?.handleAplicationsRequest(result)
        }
    }
}

// MARK: - SearchPlayersPageInteractorOutput

extension SearchPlayersPagePresenter: SearchPlayersPageInteractorOutput {}

// MARK: - SearchPlayersPageViewOutput

extension SearchPlayersPagePresenter: SearchPlayersPageViewOutput {

    func viewDidLoad() {
        moduleOutput?.moduleDidLoad(self)
        view?.setupLoadingState()
        getSerachResults()
    }

    func viewStartRefreshing() {
        getSerachResults()
    }

    func searchBarTextDidChange(text: String) {
        let filteredAplications = text.isEmpty
        ? aplications
        : aplications.filter { (item: PlayersApplicationDisplayModel) -> Bool in
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

    func wantsToOpenPlayersApplication(player: PlayersApplicationDisplayModel) {
        moduleOutput?.openPlayersApplication(player: player)
    }
}
