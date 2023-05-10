//
//  UIImage + resized.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 09.05.2023.
//

import UIKit

extension UIImage {
    func resized(to size: CGSize) -> UIImage {
        return UIGraphicsImageRenderer(size: size).image { _ in
            draw(in: CGRect(origin: .zero, size: size))
        }
    }
}
