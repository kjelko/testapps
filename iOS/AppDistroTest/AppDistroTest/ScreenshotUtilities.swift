//
//  ScreenshotUtilities.swift
//  AppDistroTest
//
//  Created by Tejas Deshpande on 12/2/22.
//

import Foundation
import UIKit

class ScreenshotUtilties {
  static func takeScreenshotImp() -> UIImage? {
    let layer = UIApplication.shared.keyWindow?.layer
    
    if let layer {
      let renderer = UIGraphicsImageRenderer(size: layer.bounds.size)
      let image = renderer.image { ctx in
        layer.render(in: ctx.cgContext)
      }
      
      return image
    }
    
    return nil
  }
}
