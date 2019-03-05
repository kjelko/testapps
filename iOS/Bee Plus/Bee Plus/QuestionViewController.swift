//
//  QuestionViewController.swift
//  Bee Plus
//
//  Created by Jeremy Durham on 3/2/19.
//  Copyright © 2019 Firebase Dev. All rights reserved.
//

import UIKit
import Firebase
import GoogleSignIn

class QuestionViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor.white

        let image = UIImage(named: "Image")
        let imageView = UIImageView(image: image)
        imageView.center = view.center
        view.addSubview(imageView)
    }
}

