//
//  RequestConfig.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

public struct RequestConfig {
    let request: RequestProtocol
 }

public struct RequestConfigWithParser<Parser> where Parser: ParserProtocol {
    let request: RequestProtocol
    let parser: Parser
 }
