//
//  RequestConfig.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

public struct RequestConfig {
    let request: IRequest
 }

public struct RequestConfigWithParser<Parser> where Parser: IParser {
    let request: IRequest
    let parser: Parser
 }
