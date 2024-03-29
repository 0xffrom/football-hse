//
//  TeamApplicationsParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import Foundation

final class TeamApplicationsParser: ParserProtocol {

    typealias Model = [TeamApplicationResponseModel]

    func parse(data: Data) -> [TeamApplicationResponseModel]? {
        guard let model = try? JSONDecoder().decode([TeamApplicationResponseModel].self, from: data) else {
            return nil
        }
        return model
    }
}

final class TeamApplicationParser: ParserProtocol {

    typealias Model = TeamApplicationResponseModel

    func parse(data: Data) -> TeamApplicationResponseModel? {
        guard let model = try? JSONDecoder().decode(TeamApplicationResponseModel.self, from: data) else {
            return nil
        }
        return model
    }
}
