//
//  Filterabale.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.04.2023.
//

import Foundation

public protocol Filterabale: Hashable {
    associatedtype Value

    static func getListOfAllValues() -> [Value]
    static func getListOfAllValuesInStringFormat() -> [String]
    static func getNumber(items: [Value]) -> Int
    func getStringValue() -> String
    static func getValueWithIndex(_ index: Int) -> Value
}
