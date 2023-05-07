//
//  UIStackView + Extensions.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 03.05.2023.
//

import UIKit

extension UIStackView {

    func remove(view: UIView) {
        removeArrangedSubview(view)
        view.removeFromSuperview()
    }

    func removeAllArrangedSubviews() {
        arrangedSubviews.forEach { (view) in
            remove(view: view)
        }
    }

}
