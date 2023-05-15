//
//  ProfileParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 04.05.2023.
//

import Foundation

final class ProfileParser: ParserProtocol {

    typealias Model = ProfileResponseModel

    func parse(data: Data) -> ProfileResponseModel? {
        guard let model = try? JSONDecoder().decode(ProfileResponseModel.self, from: data) else {
            return nil
        }
        return model
    }
}
