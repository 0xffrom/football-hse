//
//  NetworkError.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

public enum NetworkError: Error {
    case badURL
    case parsingError
    case tokensError
    case wrongParams
 }
