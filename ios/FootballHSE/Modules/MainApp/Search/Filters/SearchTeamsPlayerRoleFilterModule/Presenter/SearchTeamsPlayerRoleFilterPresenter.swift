//
//  SearchTeamsPlayerRoleFilterPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class SearchTeamsPlayerRoleFilterPresenter<T: Filterabale> {

    // MARK: Public Properties

    weak var view: SearchTeamsPlayerRoleFilterViewInput?
    weak var moduleOutput: SearchTeamsPlayerRoleFilterModuleOutput?

    // MARK: Private Properties

    private let interactor: SearchTeamsPlayerRoleFilterInteractorInput
    private let searchByFiltersService: SearchByFiltersService
    private var selectedItems: Set<T> = []

    // MARK: Lifecycle

    init(
        interactor: SearchTeamsPlayerRoleFilterInteractorInput,
        searchByFiltersService: SearchByFiltersService
    ) {
        self.interactor = interactor
        self.searchByFiltersService = searchByFiltersService
    }

    private func chengeSelectionOfItemWithIndex(index: Int) {
        let item = T.getValueWithIndex(index)
        chengeSelectionOfItem(item: item as! T)
    }

    private func chengeSelectionOfItem(item: T) {
        if selectedItems.contains(item) {
            selectedItems.remove(item)
        } else {
            selectedItems.insert(item)
        }
    }
}

// MARK: - SearchTeamsPlayerRoleFilterModuleInput

extension SearchTeamsPlayerRoleFilterPresenter: SearchTeamsPlayerRoleFilterModuleInput {}

// MARK: - SearchTeamsPlayerRoleFilterInteractorOutput

extension SearchTeamsPlayerRoleFilterPresenter: SearchTeamsPlayerRoleFilterInteractorOutput {}

// MARK: - SearchTeamsPlayerRoleFilterViewOutput

extension SearchTeamsPlayerRoleFilterPresenter: SearchTeamsPlayerRoleFilterViewOutput {

    func viewDidLoad() {
        let strings = T.getListOfAllValuesInStringFormat()
        let displayData = strings.map {
            SearchTeamsPlayerRoleFilterViewController.DisplayData.FilterCellDisplayData(
                filterName: $0,
                checkAction: chengeSelectionOfItemWithIndex
            )
        }

        view?.setupDataState(with: .init(
            data: displayData,
            title: searchByFiltersService.fltersNames[searchByFiltersService.numberOfSelectedFilters()],
            isLast: searchByFiltersService.numberOfFilters - 1 == searchByFiltersService.numberOfSelectedFilters()
        ))
    }

    func saveSelections() {
        let selectedItemsArray = selectedItems.map { $0 }
        let binaryCodeOfSelection = T.getNumber(items: selectedItemsArray as! [T.Value])

        if searchByFiltersService.mustChooseAllFilters && binaryCodeOfSelection == -1 {
            view?.showAlertMustChooseAllFilters()
            return
        }

        searchByFiltersService.addFilterSelection(selectionCode: binaryCodeOfSelection)

        if searchByFiltersService.numberOfFilters == searchByFiltersService.numberOfSelectedFilters() {
            moduleOutput?.showResults(filters: searchByFiltersService.selectionCodes)
            searchByFiltersService.clearSelection()
        } else {
            moduleOutput?.openNextFilter()
        }
    }

    func removeSelection() {
        searchByFiltersService.removeLastSelection()
    }
}
