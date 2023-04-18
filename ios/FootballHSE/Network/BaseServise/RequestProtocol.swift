//
//  RequestProtocol.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

public protocol RequestProtocol {
    var urlRequest: URLRequest? { get }
}

extension RequestProtocol {

    func paramsToString(params: [String: String]) -> String {
        let parameterArray = params.map { key, value in
            return "\(key)=\(value)"
        }

        return "&" + parameterArray.joined(separator: "&")
    }
}
