//
//  UITabBarController + Extenstions.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 12.03.2023.
//

import Foundation
import UIKit

extension UITabBarController {

    public func addViewController(_ viewController: UIViewController) {
        if var currentViewControllers = viewControllers {
            currentViewControllers.append(viewController)
            setViewControllers(currentViewControllers, animated: true)
        } else {
            setViewControllers([viewController], animated: true)
        }
    }
}
