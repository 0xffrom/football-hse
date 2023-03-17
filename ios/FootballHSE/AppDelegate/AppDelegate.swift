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

    private let currentUserConfig = CurrentUserConfig.shared
}


extension AppDelegate: UIApplicationDelegate {

    func application( _ application: UIApplication,
                      didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let rootNavigationController = UINavigationController()

        rootCoordinator = RootCoordinator(parentNavigationController: rootNavigationController, window: window!, currentUserConfig: currentUserConfig, finishHandler: nil)

        window?.rootViewController = rootNavigationController
        window?.makeKeyAndVisible()

        // let coordinator = MainCoordinator(window: window!)

        rootCoordinator?.start(animated: true)

        return true
    }
}

