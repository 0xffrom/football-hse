//
//  Formatter + iso8601.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 15.05.2023.
//

import Foundation

extension Formatter {

    static let iso8601: ISO8601DateFormatter = {
        let dateFormatter = ISO8601DateFormatter()
        dateFormatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        return dateFormatter
    }()
}
