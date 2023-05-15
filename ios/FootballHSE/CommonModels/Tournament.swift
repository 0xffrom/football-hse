//
//  Tournaments.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.04.2023.
//

import Foundation

enum Tournament: Int, Codable, CaseIterable {
    case autumnCup
    case futsalYouthLeague
    case futsalSecondLeague
    case futsalFirstLeague
    case futsalMajorLeague
    case youthLeague
    case secondLeague
    case firstLeague
    case majorLeague
    case summerCup
}

extension Tournament {

    static func getMaxCountOfTournaments() -> Int {
        return Tournament.allCases.count
    }

    static func convertIntToTournaments(num: Int?) -> [Tournament] {
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

        let tournaments = powers.compactMap { power in
            Tournament(rawValue: power)
        }
        return tournaments
    }
}

extension Tournament: Filterabale {

    static func getValueWithIndex(_ index: Int) -> Tournament {
        Tournament.allCases[index]
    }

    static func getIndexOfValue(_ item: Tournament) -> Int? {
        Tournament.allCases.firstIndex(of: item)
    }

    static func getNumber(items: [Tournament]) -> Int {
        if items.isEmpty { return -1 }
        var num = 0
        items.forEach { item in
            guard let idx = getIndexOfValue(item) else { return }
            num += Int.pow(2, idx)
        }
        return num
    }

    static func getListOfAllValues() -> [Tournament] {
        var arr: [Tournament] = []
        for value in Tournament.allCases {
            arr.append(value)
        }
        return arr
    }

    static func getListOfAllValuesInStringFormat() -> [String] {
        var arr: [String] = []
        for value in Tournament.allCases {
            arr.append(value.getStringValue())
        }
        return arr
    }

    func getStringValue() -> String {
        switch self {
        case .autumnCup:
            return "Осенний кубок"
        case .futsalYouthLeague:
            return "Футзал Молодежная лига"
        case .futsalSecondLeague:
            return "Футзал Вторая лига"
        case .futsalFirstLeague:
            return "Футзал Первая лига"
        case .futsalMajorLeague:
            return "Футзал Высшая лига"
        case .youthLeague:
            return "Молодежная лига"
        case .secondLeague:
            return "Вторая лига"
        case .firstLeague:
            return "Первая лига"
        case .majorLeague:
            return "Высшая лига"
        case .summerCup:
            return "Летний кубок"
        }
    }
}
