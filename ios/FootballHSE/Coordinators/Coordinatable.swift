//
//  Coordinatable.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.02.2023.
//

import Foundation

protocol Coordinatable: AnyObject {

    func start(animated: Bool)
    func finish(animated: Bool, completion: (() -> Void)?)
}

extension Coordinatable {

    func finish(animated: Bool) {
        finish(animated: animated, completion: nil)
    }
}

extension Array where Element == Coordinatable {

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
