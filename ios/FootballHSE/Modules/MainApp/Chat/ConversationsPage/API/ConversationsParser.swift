//
//  ConversationsParser.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 21.04.2023.
//

import Foundation

final class ConversationsParser: ParserProtocol {

    typealias Model = [ConversationResponseMosel]

    func parse(data: Data) -> [ConversationResponseMosel]? {
        guard let model = try? JSONDecoder().decode([ConversationResponseMosel].self, from: data) else {
            return nil
        }
        return model
    }
}

final class ConversationParser: ParserProtocol {

    typealias Model = ConversationResponseMosel

    func parse(data: Data) -> ConversationResponseMosel? {
        guard let model = try? JSONDecoder().decode(ConversationResponseMosel.self, from: data) else {
            return nil
        }
        return model
    }
}
