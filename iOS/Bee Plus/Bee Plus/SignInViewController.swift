//
//  SignInViewController.swift
//  Bee Plus
//
//  Created by Jeremy Durham on 3/2/19.
//  Copyright © 2019 Firebase Dev. All rights reserved.
//

import UIKit
import Firebase
import GoogleSignIn
import RevealingSplashView

class SignInViewController: UIViewController, GIDSignInUIDelegate, GIDSignInDelegate {
    //when the signin complets
    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error!) {
        
        //if any error stop and print the error
        if error != nil{
            print(error ?? "google error")
            return
        }
        
        let questionViewController:QuestionViewController = QuestionViewController()
        self.present(questionViewController, animated: true)
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        let revealingSplashView = RevealingSplashView(iconImage: UIImage(named: "SplashImage")!,iconInitialSize: CGSize(width: 70, height: 70), backgroundColor: UIColor(red:0.11, green:0.56, blue:0.95, alpha:1.0))

        revealingSplashView.backgroundColor = .white
        self.view.addSubview(revealingSplashView)
        
        GIDSignIn.sharedInstance().uiDelegate = self
        GIDSignIn.sharedInstance().delegate = self
        
        revealingSplashView.startAnimation() {
            let googleSignInButton = GIDSignInButton()
            googleSignInButton.center = self.view.center
            self.view.backgroundColor = .white
            self.view.addSubview(googleSignInButton)
        }
    }


}

