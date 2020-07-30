//
//  AppDelegate.swift
//  Bee Plus
//
//  Created by Jeremy Durham on 3/2/19.
//  Copyright © 2019 Firebase Dev. All rights reserved.
//

import UIKit
import FirebaseCore
import FirebaseAppDistribution
import GoogleSignIn

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        setupFirebase()
        setupView()

        return true
    }
    
    @available(iOS 9.0, *)
    func application(_ application: UIApplication, open url: URL, options:[UIApplication.OpenURLOptionsKey : Any]) -> Bool {
            return GIDSignIn.sharedInstance().handle(url, sourceApplication:options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String,
                annotation: [:])
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

