//
//  TeamParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 06.05.2023.
//

import Foundation

final class TeamParser: ParserProtocol {

    typealias Model = TeamModel

    func parse(data: Data) -> TeamModel? {
        guard let model = try? JSONDecoder().decode(TeamModel.self, from: data) else {
            return nil
        }
        return model
    }
}
