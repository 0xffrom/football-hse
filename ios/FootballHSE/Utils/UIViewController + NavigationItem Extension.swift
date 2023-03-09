//
//  UIViewController + NavigationItem Extension.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 06.03.2023.
//

import UIKit

extension UIViewController {
    open override func awakeAfter(using coder: NSCoder) -> Any? {
        navigationItem.backButtonDisplayMode = .minimal
        return super.awakeAfter(using: coder)
    }
}
