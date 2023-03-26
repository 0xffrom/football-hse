//
//  Coordinatable.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.02.2023.
//

import Foundation
import UIKit

protocol Coordinatable: AnyObject {

    func start(animated: Bool)
    func finish(animated: Bool, completion: (() -> Void)?)
}

extension Coordinatable {

    func finish(animated: Bool) {
        finish(animated: animated, completion: nil)
    }

    func switchViewController(_ viewController: UIViewController?, in window: UIWindow) {
        guard let viewController = viewController else { return }
        if let rootViewController = window.rootViewController {
            rootViewController.dismiss(animated: false) { [unowned self] in
                self.setupRoot(viewController, in: window)
            }
        } else {
            setupRoot(viewController, in: window)
        }
    }

    func setupRoot(_ viewController: UIViewController, in window: UIWindow) {
        window.rootViewController = viewController
        UIView.transition(
            with: window,
            duration: 0.4,
            options: .transitionCrossDissolve,
            animations: nil
        )
    }
}

extension Array where Element == Coordinatable {

    func getCoordinator<T: Coordinatable>(ofType type: T.Type) -> T? {
        first(
            where: { $0 is T }
        ) as? T
    }

    mutating func removeCoordinator<T: Coordinatable>(ofType type: T.Type) {
        guard let index = firstIndex(
            where: { $0 is T }
        ) else {
            return
        }

        remove(at: index)
    }

    func finishAll(animated: Bool, completion: (() -> Void)? = nil) {
        guard let coordinator = last else {
            completion?()
            return
        }
        var coordinators = self
        coordinator.finish(animated: animated) {
            coordinators.removeLast()
            coordinators.finishAll(animated: animated, completion: completion)
        }
    }
}
