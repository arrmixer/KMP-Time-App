//
//  TimezoneDialog.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 8/29/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

extension String: Identifiable {
    public var id: String { return self }
}

struct TimezoneDialog: View {
    @State private var searchText: String = ""
    @EnvironmentObject var timezoneItems: TimezoneItems
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            Searchbar(text: $searchText)
            List(selection: $timezoneItems.selectedTimezones) {
                ForEach(
                    timezoneItems.timezones.filter {
                        searchText.isEmpty ? true : $0.lowercased().contains(searchText.lowercased())
                    },
                    id: \.self) { timezone in
                        HStack {
                            Image(systemName: timezoneItems.selectedTimezones.contains(timezone) ? "checkmark.circle" : "circle")
                                .onTapGesture {
                                    selectTimezone(timezone: timezone)
                                }
                            Text(timezone)
                                .onTapGesture {
                                    selectTimezone(timezone: timezone)
                                }
                        }
                    }
            }
        }
        Button("Dismiss") {
            presentationMode.wrappedValue.dismiss()
        }
    }
    func selectTimezone(timezone: String) {
        if timezoneItems.selectedTimezones.contains(timezone) {
            timezoneItems.selectedTimezones.remove(timezone)
        } else {
            timezoneItems.selectedTimezones.insert(timezone)
        }
    }
}


struct TimezoneDialog_Previews: PreviewProvider {
    static var previews: some View {
        TimezoneDialog().environmentObject(TimezoneItems())
    }
}
