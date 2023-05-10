//
//  String + Date Parsing.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 09.05.2023.
//

import Foundation

extension String {

    func getDateFromString() -> Date? {
        let dateFormatter = ISO8601DateFormatter()
        dateFormatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        let date = dateFormatter.date(from: self)

        return date
    }

    func convertToMinutes() -> String? {
        let formatter = DateFormatter()
        formatter.dateFormat = "HH:mm"

        guard let date = self.getDateFromString() else {
            return nil
        }
        return formatter.string(from: date)
    }

    static func convertDateToISOString(date: Date) -> String {
        let dateFormatter = ISO8601DateFormatter()
        dateFormatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        let dateString = dateFormatter.string(from: date)

        return dateString
    }

    static func fromDateToString(from date: Date) -> String {
        let formatter = DateFormatter()
        if (Calendar.current.isDateInToday(date)) {
            formatter.dateFormat = "HH:mm a"
            return formatter.string(from: date)
        }

        formatter.timeStyle = .none
        formatter.dateStyle = .medium
        formatter.locale = Locale(identifier: "ru_RU")
        formatter.doesRelativeDateFormatting = true
        return formatter.string(from: date)
    }
}
