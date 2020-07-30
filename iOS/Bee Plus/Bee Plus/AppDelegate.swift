//
//  AppDelegate.swift
//  Bee Plus
//
//  Created by Jeremy Durham on 3/2/19.
//  Copyright © 2019 Firebase Dev. All rights reserved.
//

import FirebaseAppDistribution
import FirebaseCore
import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_: UIApplication, didFinishLaunchingWithOptions _: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        setupFirebase()
        setupView()

        return true
    }

    func setupFirebase() {
        FirebaseApp.configure()
    }

    func setupView() {
        // We've opted not to use storyboards, so instead set up the view and view controller ourselves
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.makeKeyAndVisible()
        window?.rootViewController = MainViewController()
    }
}
