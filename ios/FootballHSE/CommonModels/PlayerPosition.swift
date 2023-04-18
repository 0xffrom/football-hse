//
//  PlayerPosition.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

enum PlayerPosition: Int, Codable {
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

    static func convertIntToPositions(num: Int) -> [PlayerPosition] {
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

    func getNameOfRole() -> String {
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
