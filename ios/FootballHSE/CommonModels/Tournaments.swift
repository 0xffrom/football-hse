//
//  Tournaments.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.04.2023.
//

import Foundation

enum Tournaments: Int, Codable {
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

extension Tournaments {

    static func getMaxCountOfTournaments() -> Int {
        return 10
    }

    static func convertIntToTournaments(num: Int) -> [Tournaments] {
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
            Tournaments(rawValue: power)
        }
        return tournaments
    }

    func getNameOfTournament() -> String {
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
