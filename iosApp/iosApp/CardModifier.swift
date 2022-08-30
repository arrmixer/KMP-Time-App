//
//  CardModifier.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 8/29/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct CardModifier: ViewModifier {
  func body(content: Content) -> some View {
    content
      .cornerRadius(16)
      .shadow(color: Color.black.opacity(0.2), radius: 16, x: 0, y: 0)
  }
}
