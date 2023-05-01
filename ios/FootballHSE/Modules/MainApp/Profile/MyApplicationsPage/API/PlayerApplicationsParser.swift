//
//  PlayerApplicationsParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 21.04.2023.
//

import Foundation

final class PlayerApplicationsParser: ParserProtocol {

    typealias Model = [PlayerApplicationResponseModel]

    func parse(data: Data) -> [PlayerApplicationResponseModel]? {
        guard let model = try? JSONDecoder().decode([PlayerApplicationResponseModel].self, from: data) else {
            return nil
        }
        return model
    }
}

final class PlayerApplicationParser: ParserProtocol {

    typealias Model = PlayerApplicationResponseModel

    func parse(data: Data) -> PlayerApplicationResponseModel? {
        guard let model = try? JSONDecoder().decode(PlayerApplicationResponseModel.self, from: data) else {
            return nil
        }
        return model
    }
}
