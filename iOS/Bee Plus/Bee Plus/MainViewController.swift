//
//  SignInViewController.swift
//  Bee Plus
//
//  Created by Jeremy Durham on 3/2/19.
//  Copyright © 2019 Firebase Dev. All rights reserved.
//

import FirebaseAppDistribution
import UIKit

class MainViewController: UIViewController {
    var checkForUpdateButton: UIButton?
    var signInOutButton: UIButton?
    var signedInStatus: UILabel?

    override func viewDidLoad() {
        super.viewDidLoad()

        view.backgroundColor = .white

        checkForUpdateButton = UIButton(frame: CGRect(x: 50, y: 200, width: 300, height: 50))
        checkForUpdateButton!.backgroundColor = .lightGray
        checkForUpdateButton!.setTitle("Check for Update Manually", for: .normal)
        checkForUpdateButton!.addTarget(self, action: #selector(checkForUpdateButtonClicked), for: .touchUpInside)
        view.addSubview(checkForUpdateButton!)

        signInOutButton = UIButton(frame: CGRect(x: 50, y: 380, width: 300, height: 50))
        signInOutButton!.backgroundColor = .lightGray
        let title = AppDistribution.appDistribution().isTesterSignedIn ? "Sign Out" : "Sign In"
        signInOutButton!.setTitle(title, for: .normal)
        signInOutButton!.isHidden = false
        signInOutButton!.addTarget(self, action: #selector(signInOutButtonClicked), for: .touchUpInside)
        view.addSubview(signInOutButton!)

        signedInStatus = UILabel(frame: CGRect(x: 50, y: 540, width: 400, height: 60))
        signedInStatus!.textColor = .black
        signedInStatus!.backgroundColor = .white
        signedInStatus!.font = .systemFont(ofSize: 16)
        signedInStatus!.text = AppDistribution.appDistribution().isTesterSignedIn ? "Tester is signed in" : "Tester is signed out"
        view.addSubview(signedInStatus!)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)

        AppDistribution.appDistribution().checkForUpdate { release, error in
            if error != nil {
                let uiAlert = UIAlertController(title: "Custom:Error", message: "Error during tester sign in! \(error?.localizedDescription ?? "")", preferredStyle: .alert)
                uiAlert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default) {
                    _ in
                })
                return
            }

            if AppDistribution.appDistribution().isTesterSignedIn {
                self.signInOutButton!.setTitle("Sign Out", for: .normal)
                self.signedInStatus!.text = "Tester is signed in"
            }

            guard let release = release else { return }

            let title = "New Version Available"
            let message = "Version \(release.displayVersion)(\(release.buildVersion)) is available."
            let uialert = UIAlertController(title: title, message: message, preferredStyle: .alert)

            uialert.addAction(UIAlertAction(title: "Update", style: UIAlertAction.Style.default) {
                _ in
                UIApplication.shared.open(release.downloadURL)
            })
            uialert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel) {
                _ in
            })

            // self should be a UIViewController.
            self.present(uialert, animated: true, completion: nil)
        }
    }

    @objc func checkForUpdateButtonClicked() {
        AppDistribution.appDistribution().checkForUpdate(completion: { _, error in
            var uiAlert: UIAlertController

            if error != nil {
                uiAlert = UIAlertController(title: "Error", message: "Error Checking for update! \(error?.localizedDescription ?? "")", preferredStyle: .alert)
            } else {
                uiAlert = UIAlertController(title: "Check for Update", message: "No releases found!", preferredStyle: .alert)
            }

            if AppDistribution.appDistribution().isTesterSignedIn {
                self.signInOutButton!.setTitle("Sign Out", for: .normal)
                self.signedInStatus!.text = "Tester is signed in"
            }

            uiAlert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default))
            self.present(uiAlert, animated: true, completion: nil)
        })
    }

    @objc func signInOutButtonClicked() {
        if AppDistribution.appDistribution().isTesterSignedIn {
            AppDistribution.appDistribution().signOutTester()
            signInOutButton!.setTitle("Sign In", for: .normal)
            signedInStatus!.text = "Tester is signed out"
        } else {
            AppDistribution.appDistribution().signInTester(completion: { error in
                if error == nil {
                    self.signInOutButton!.setTitle("Sign Out", for: .normal)
                    self.signedInStatus!.text = "Tester is signed in"
                    self.checkForUpdateButton!.isHidden = false
                } else {
                    let uiAlert = UIAlertController(title: "Custom:Error", message: "Error during tester sign in! \(error?.localizedDescription ?? "")", preferredStyle: .alert)
                    uiAlert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default) {
                        _ in
                    })

                    self.present(uiAlert, animated: true, completion: nil)
                }
            })
        }
    }
}
