//
//  MyApplicationsPagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class MyApplicationsPagePresenter {

    // MARK: Public Properties

    weak var view: MyApplicationsPageViewInput?
    var moduleOutput: MyApplicationsPageModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: MyApplicationsPageInteractorInput

    // MARK: Lifecycle

    init(interactor: MyApplicationsPageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private

    private func handleAplicationsRequest(_ result: Result<[ProfilePlayerApplicationDisplayModel], Error>) {
        switch result {
        case .success(let data):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                if data.isEmpty {
                    view.setupEmptyState()
                } else {
                    view.setupDataState(with: data)
                }
            }
        case .failure(_):
            DispatchQueue.main.async { [weak self] in
                guard let self = self, let view = self.view else { return }
                view.setupErrorState()
            }
        }
    }

    private func getApplications() {
        interactor.getApplications { [weak self] result in
            self?.handleAplicationsRequest(result)
        }
    }
}

// MARK: - MyApplicationsPageModuleInput

extension MyApplicationsPagePresenter: MyApplicationsPageModuleInput {}

// MARK: - MyApplicationsPageInteractorOutput

extension MyApplicationsPagePresenter: MyApplicationsPageInteractorOutput {}

// MARK: - MyApplicationsPageViewOutput

extension MyApplicationsPagePresenter: MyApplicationsPageViewOutput {

    func viewDidLoad() {
        view?.setupLoadingState()
        getApplications()
    }

    func viewStartRefreshing() {
        getApplications()
    }
}
