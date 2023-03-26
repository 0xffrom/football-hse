//
//  PlayerPosition.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

enum PlayerPosition: Int, Codable {
    case CFRV = 0
    case LFRV
    case PFRV
    case CAP
    case COP
    case LP
    case PP
    case TS
    case LS
    case PS
    case VRT
}

extension PlayerPosition {

    static func convertIntToPositions(num: Int) -> [PlayerPosition] {
        let binaryStr = String(num, radix: 2)

        var powers: [Int] = []

        if num % 2 == 1 {
            powers.append(0)
        }

        for index in 1..<binaryStr.count {
            let strIdx = binaryStr.index(binaryStr.startIndex, offsetBy: index)
            if binaryStr[strIdx] == "1" {
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
        case .CFRV:
            return "ЦФРВ"
        case .LFRV:
            return "ЛФРВ"
        case .PFRV:
            return "ПФРВ"
        case .CAP:
            return "ЦАП"
        case .COP:
            return "ЦОП"
        case .LP:
            return "ЛП"
        case .PP:
            return "ПП"
        case .TS:
            return "ЦЗ"
        case .LS:
            return "ЛЗ"
        case .PS:
            return "ПЗ"
        case .VRT:
            return "ВРТ"
        }
    }
}
