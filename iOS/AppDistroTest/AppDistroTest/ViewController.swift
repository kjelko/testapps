//
//  ViewController.swift
//  AppDistroTest
//
//  Created by Tejas Deshpande on 9/28/22.
//

import UIKit
import FirebaseAppDistribution
import UserNotifications
import Photos

class ViewController: UIViewController {

  @IBOutlet weak var imageView: UIImageView!
  
  override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
      listenForScreenshot()
    
      UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { success, error in
          if success {
              print("All set!")
          } else if let error = error {
              print(error.localizedDescription)
          }
      }
    }
  
  func listenForScreenshot() {
    let mainQueue = OperationQueue.main
    NotificationCenter.default.addObserver(forName: UIApplication.userDidTakeScreenshotNotification, object: nil, queue: mainQueue) { notification in
      self.imageView.image = ScreenshotUtilties.takeScreenshotImp()
      print("Identified screenshot")
    }
  }
  
  @IBAction func signInTap(_ sender: Any) {
    AppDistribution.appDistribution().signInTester() { error in
      print(error ?? "No Error")
    }
  }
  
  @IBAction func checkForUpdateAction(_ sender: Any) {
    AppDistribution.appDistribution().checkForUpdate(completion: { release, error in
      if error != nil {
          // Handle error
          print(error ?? "No Error")
          return
      }

      guard let release = release else {
        return
      }

      // Customize your alerts here.
      let title = "New Version Available"
      let message = "Version \(release.displayVersion)(\(release.buildVersion)) is available."
      let uialert = UIAlertController(title: title,message: message, preferredStyle: .alert)

      uialert.addAction(UIAlertAction(title: "Update", style: UIAlertAction.Style.default) {
        _ in
        UIApplication.shared.open(release.downloadURL)
      })
      uialert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel) {
        _ in
      })

      // self should be a UIViewController.
      self.present(uialert, animated: true, completion: nil)
    })
  }
  
  @IBAction func signOutAction(_ sender: Any) {
    AppDistribution.appDistribution().signOutTester()
  }
  
  @IBAction func sendLocalNotificationAction(_ sender: Any) {
    let content = UNMutableNotificationContent()
    content.title = "Send Feedback"
    content.body = "Send feedback for the app"
    content.sound = UNNotificationSound.default
        
    let uuidString = UUID().uuidString
    let request = UNNotificationRequest(identifier: uuidString,
                content: content, trigger: nil)
    
    UNUserNotificationCenter.current().add(request)
  }
  
  
  @IBAction func takeScreenshot(_ sender: Any) {
    self.imageView.image = ScreenshotUtilties.takeScreenshotImp()
  }
  
  @IBAction func openFeedbackVC(_ sender: Any) {
    AppDistribution.appDistribution().startFeedback(additionalFormText: "Test")
  }
  
  @IBAction func saveScreenshot(_ sender: Any) {
    PHPhotoLibrary.requestAuthorization { status in
            guard status == .authorized else { return }
      
            DispatchQueue.main.async {
              let data = self.imageView.image?.pngData()
              PHPhotoLibrary.shared().performChanges({
                  // Add the captured photo's file data as the main resource for the Photos asset.
                  let creationRequest = PHAssetCreationRequest.forAsset()
                creationRequest.addResource(with: .photo, data: data!, options: nil)
              }, completionHandler: nil)
            }
        }
  }
}

