//
//  PlayerPosition.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

enum PlayerPosition: Int, Codable, CaseIterable {
    case сentralForward
    case leftForward
    case rightForward
    case centralAttackingMidfielder
    case defensiveMidfielder
    case leftMidfielder
    case rightMidfielder
    case centralDefender
    case leftDefender
    case rightDefender
    case goalkeeper
}

extension PlayerPosition {

    static func convertIntToPositions(num: Int?) -> [PlayerPosition] {
        guard let num else { return [] }

        let binaryStr = String(num, radix: 2)

        var powers: [Int] = []

        let reversedBinaryStr = binaryStr.reversed
        for index in 0..<reversedBinaryStr.count {
            let idx = binaryStr.index(reversedBinaryStr.startIndex, offsetBy: index)
            if reversedBinaryStr[idx] == "1" {
                powers.append(index)
            }
        }

        let positions = powers.compactMap { power in
            PlayerPosition(rawValue: power)
        }
        return positions
    }
}

extension PlayerPosition: Filterabale {

    static func getValueWithIndex(_ index: Int) -> PlayerPosition {
        PlayerPosition.allCases[index]
    }

    static func getIndexOfValue(_ item: PlayerPosition) -> Int? {
        PlayerPosition.allCases.firstIndex(of: item)
    }

    static func getNumber(items: [PlayerPosition]) -> Int {
        if items.isEmpty { return -1 }
        var num = 0
        items.forEach { item in
            guard let idx = getIndexOfValue(item) else { return }
            num += Int.pow(2, idx)
        }
        return num
    }

    static func getListOfAllValues() -> [PlayerPosition] {
        var arr: [PlayerPosition] = []
        for value in PlayerPosition.allCases {
            arr.append(value)
        }
        return arr
    }

    static func getListOfAllValuesInStringFormat() -> [String] {
        var arr: [String] = []
        for value in PlayerPosition.allCases {
            arr.append(value.getStringValue())
        }
        return arr
    }

    func getStringValue() -> String {
        switch self {
        case .сentralForward:
            return "Центральный форвард"
        case .leftForward:
            return "Левый форвард"
        case .rightForward:
            return "Правый форвард"
        case .centralAttackingMidfielder:
            return "Центральный атакующий полузащитник"
        case .defensiveMidfielder:
            return "Опорный полузащитник"
        case .leftMidfielder:
            return "Левый полузащитник"
        case .rightMidfielder:
            return "Правый полузащиник"
        case .centralDefender:
            return "Центральный защитник"
        case .leftDefender:
            return "Левый защитник"
        case .rightDefender:
            return "Правый защитник"
        case .goalkeeper:
            return "Голкипер"
        }
    }
}
