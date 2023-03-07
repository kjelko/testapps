//
//  ViewControllerTwo.swift
//  AppDistroTest
//
//  Created by Tejas Deshpande on 12/2/22.
//

import UIKit

class ViewControllerTwo: UIViewController {

  @IBOutlet weak var imageView: UIImageView!
  
  override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
  
  
  @IBAction func takeScreenshotAction(_ sender: Any) {
    self.imageView.image = ScreenshotUtilties.takeScreenshotImp()
  }
  

}
