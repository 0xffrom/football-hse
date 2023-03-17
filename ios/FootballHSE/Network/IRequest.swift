//
//  IRequest.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

public protocol IRequest {
    var urlRequest: URLRequest? { get }
}

extension IRequest {

    func paramsToString(params: [String: String]) -> String {
        let parameterArray = params.map { key, value in
            return "\(key)=\(value)"
        }

        return "&" + parameterArray.joined(separator: "&")
    }
}
