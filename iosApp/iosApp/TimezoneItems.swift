//
//  TimezoneItems.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 8/29/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

class TimezoneItems: ObservableObject {
  @Published var timezones: [String] = []
  @Published var selectedTimezones = Set<String>()

  init() {
      self.timezones = TimeZoneHelperImpl().getTimeZoneStrings()
  }
}
