//
//  TeamParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 06.05.2023.
//

import Foundation

final class TeamParser: ParserProtocol {

    typealias Model = TeamResponseModel

    func parse(data: Data) -> TeamResponseModel? {
        guard let model = try? JSONDecoder().decode(TeamResponseModel.self, from: data) else {
            return nil
        }
        return model
    }
}
