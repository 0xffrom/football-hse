//
//  Int + Pow.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 19.04.2023.
//

import Foundation

extension Int {

    static func pow (_ base: Int, _ power: Int) -> Int {
      var answer : Int = 1
      for _ in 0..<power { answer *= base }
      return answer
    }
}
