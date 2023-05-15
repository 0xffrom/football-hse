//
//  SearchByFiltersService.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 19.04.2023.
//

import Foundation

protocol SearchByFiltersServiceProtocol {
    var numberOfFilters: Int { get }
    var fltersNames: [String] { get }
    var selectionCodes: [Int] { get }
    var mustChooseAllFilters: Bool { get }

    init(numberOfFilters: Int, fltersNames: [String], mustChooseAllFilters: Bool)

    func addFilterSelection(selectionCode: Int)
    func numberOfSelectedFilters() -> Int
    func removeLastSelection()
    func clearSelection()
}

public final class SearchByFiltersService: SearchByFiltersServiceProtocol {

    let numberOfFilters: Int
    let fltersNames: [String]
    let mustChooseAllFilters: Bool

    private(set) var selectionCodes: [Int] = []

    init(numberOfFilters: Int, fltersNames: [String], mustChooseAllFilters: Bool) {
        self.numberOfFilters = numberOfFilters
        self.fltersNames = fltersNames
        self.mustChooseAllFilters = mustChooseAllFilters
    }

    func addFilterSelection(selectionCode: Int) {
        selectionCodes.append(selectionCode)
    }

    func removeLastSelection() {
        if !selectionCodes.isEmpty {
            selectionCodes.removeLast()
        }
    }

    func clearSelection() {
        selectionCodes = []
    }

    func numberOfSelectedFilters() -> Int {
        selectionCodes.count
    }
}
