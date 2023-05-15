//
//  AppDelegate.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

@main
final class AppDelegate: UIResponder {

    private var rootCoordinator: Coordinatable?
    lazy var window: UIWindow? = UIWindow(frame: UIScreen.main.bounds)
    var layer: CAEmitterLayer?
}


extension AppDelegate: UIApplicationDelegate {

    func application( _ application: UIApplication,
                      didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let rootNavigationController = UINavigationController()

        rootCoordinator = RootCoordinator(
            parentNavigationController: rootNavigationController,
            window: window!,
            finishHandler: nil
        )

        window?.rootViewController = rootNavigationController
        window?.makeKeyAndVisible()

        rootCoordinator?.start(animated: true)

        return true
    }
}

