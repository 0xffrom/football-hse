//
//  ConversationsParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 21.04.2023.
//

import Foundation

final class MessagesParser: ParserProtocol {

    typealias Model = [MessageModel]

    func parse(data: Data) -> [MessageModel]? {
        guard let model = try? JSONDecoder().decode([MessageModel].self, from: data) else {
            return nil
        }
        return model
    }
}
