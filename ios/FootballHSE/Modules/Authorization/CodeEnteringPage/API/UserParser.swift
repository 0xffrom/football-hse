//
//  UserParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 26.03.2023.
//

import Foundation

final class UserParser: ParserProtocol {

    typealias Model = User

    func parse(data: Data) -> User? {
        guard let model = try? JSONDecoder().decode(User.self, from: data) else {
            return nil
        }
        return model
    }
}
