//
//  AppDelegate.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

@main
final class AppDelegate: UIResponder {

    lazy var window: UIWindow? = UIWindow(frame: UIScreen.main.bounds)
    var layer: CAEmitterLayer?
}


extension AppDelegate: UIApplicationDelegate {

    func application( _ application: UIApplication,
                      didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let rootNavigationController = UINavigationController()
        let coordinator = RootCoordinator(parentNavigationController: rootNavigationController, finishHandler: nil)

        window?.rootViewController = rootNavigationController
        window?.makeKeyAndVisible()

        coordinator.start(animated: true)

        return true
    }
}

