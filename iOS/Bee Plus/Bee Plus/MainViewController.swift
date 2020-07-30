//
//  SignInViewController.swift
//  Bee Plus
//
//  Created by Jeremy Durham on 3/2/19.
//  Copyright © 2019 Firebase Dev. All rights reserved.
//

import UIKit
import FirebaseAppDistribution
import GoogleSignIn

class MainViewController: UIViewController {
  var checkForUpdateButton: UIButton?;
  var signInOutButton: UIButton?;
  
  override func viewDidLoad() {
    super.viewDidLoad()

    self.view.backgroundColor = .white
    
    self.checkForUpdateButton = UIButton(frame: CGRect(x: 50, y: 200, width: 300, height: 50));
    self.checkForUpdateButton!.backgroundColor = .lightGray
    self.checkForUpdateButton!.setTitle("Check for Update Manually", for: .normal)
    self.checkForUpdateButton!.addTarget(self, action:#selector(self.checkForUpdateButtonClicked), for: .touchUpInside)
    self.view.addSubview(self.checkForUpdateButton!)
    
    self.signInOutButton = UIButton(frame: CGRect(x: 50, y: 380, width: 300, height: 50))
    self.signInOutButton!.backgroundColor = .lightGray
    let title = AppDistribution.appDistribution().isTesterSignedIn ? "Sign Out" : "Sign In";
    self.signInOutButton!.setTitle(title, for: .normal)
    self.signInOutButton!.isHidden = false
    self.signInOutButton!.addTarget(self, action:#selector(self.signInOutButtonClicked), for: .touchUpInside)
    self.view.addSubview(self.signInOutButton!)
  }
  
  override func viewDidAppear(_ animated: Bool) {
    super.viewDidAppear(animated)
    
    AppDistribution.appDistribution().checkForUpdate { (release, error) in
      if (error != nil) {
        let uiAlert = UIAlertController(title: "Custom:Error",message: "Error during tester sign in! \(error?.localizedDescription ?? "")", preferredStyle: .alert)
        uiAlert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default) {
             alert in
         })
        return
      }
      
      if(AppDistribution.appDistribution().isTesterSignedIn) {
        self.signInOutButton!.setTitle("Sign Out", for: .normal)
      }
      
      guard let release = release else { return }

      let title = "New Version Available"
      let message = "Version \(release.displayVersion)(\(release.buildVersion)) is available."
      let uialert = UIAlertController(title: title,message: message, preferredStyle: .alert)

      uialert.addAction(UIAlertAction(title: "Update", style: UIAlertAction.Style.default) {
        alert in
        UIApplication.shared.open(release.downloadURL)
      })
      uialert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel) {
        alert in
      })

      // self should be a UIViewController.
      self.present(uialert, animated: true, completion: nil)
    }
  }
  
  @objc func checkForUpdateButtonClicked() {
    AppDistribution.appDistribution().checkForUpdate(completion: { release, error in
      var uiAlert: UIAlertController;

      if (error != nil) {
        uiAlert = UIAlertController(title: "Error", message: "Error Checking for update! \(error?.localizedDescription ?? "")", preferredStyle: .alert)
      } else {
        uiAlert = UIAlertController(title: "Check for Update", message: "No releases found!", preferredStyle: .alert)
      }
      
      if(AppDistribution.appDistribution().isTesterSignedIn) {
        self.signInOutButton!.setTitle("Sign Out", for: .normal)
      }
      
      uiAlert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default));
      self.present(uiAlert, animated: true, completion: nil)
    })
  }
  
  @objc func signInOutButtonClicked() {
    if(AppDistribution.appDistribution().isTesterSignedIn) {
      AppDistribution.appDistribution().signOutTester()
      signInOutButton!.setTitle("Sign In", for: .normal)
    } else {
      AppDistribution.appDistribution().signInTester(completion: { error in
        if(error == nil) {
            self.signInOutButton!.setTitle("Sign Out", for: .normal)
            self.checkForUpdateButton!.isHidden = false
        } else {
          let uiAlert = UIAlertController(title: "Custom:Error",message: "Error during tester sign in! \(error?.localizedDescription ?? "")", preferredStyle: .alert)
            uiAlert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default) {
                 alert in
             })

          self.present(uiAlert, animated: true, completion: nil)
        }
      })
    }
  }
}

