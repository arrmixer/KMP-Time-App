//
//  FindMeeting.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 9/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FindMeeting: View {
    // Create a timezoneItems environment variable. This will come from ContentView.
    @EnvironmentObject private var timezoneItems: TimezoneItems
    // Create an instance of the TimeZoneHelperImpl class
    private var timezoneHelper = TimeZoneHelperImpl()
    // An array for meeting hours that all can meet at
    @State private var meetingHours: [Int] = []
    @State private var showHoursDialog = false
    // Start and end dates that are 8 a.m. and 5 p.m.
    @State private var startDate = Calendar.current.date(bySettingHour: 8, minute: 0, second: 0, of: Date())!
    @State private var endDate = Calendar.current.date(bySettingHour: 17, minute: 0, second: 0, of: Date())!

    var body: some View {
        NavigationView {
          VStack {
            Spacer()
              .frame(height: 8)
              Form {
                Section(header: Text("Time Range")) {
                    // Start time date picker.
                    DatePicker("Start Time", selection: $startDate, displayedComponents: .hourAndMinute)
                    // End time date picker.
                    DatePicker("End Time", selection: $endDate, displayedComponents: .hourAndMinute)
                }
                Section(header: Text("Time Zones")) {
                  // List of selected time zones.
                  ForEach(Array(timezoneItems.selectedTimezones), id: \.self) {  timezone in
                    HStack {
                      Text(timezone)
                      Spacer()
                    }
                  }
                }
              } // Form
              Spacer()
              Button(action: {
                // Clear your array of any previous values
                meetingHours.removeAll()
                // Get the start and end hours.
                let startHour = Calendar.current.component(.hour, from: startDate)
                let endHour = Calendar.current.component(.hour, from: endDate)
                // Call the shared library search method, converting the hours to ints
                let hours = timezoneHelper.search(
                  startHour: Int32(startHour),
                  endHour: Int32(endHour),
                  timezoneStrings: Array(timezoneItems.selectedTimezones))
                // Create another array of ints from the hours returned. Convert to iOS ints.
                let hourInts = hours.map { kotinHour in
                  Int(truncating: kotinHour)
                }
                meetingHours += hourInts
                // Set the flag to show the hours dialog.
                showHoursDialog = true
              }, label: {
                Text("Search")
                  .foregroundColor(Color.black)
              })
              Spacer()
                .frame(height: 8)
          } // VStack
          .navigationTitle("Find Meeting Time")
          .sheet(isPresented: $showHoursDialog) {
            HourSheet(hours: $meetingHours)
          }
        } // NavigationView
    }
}

struct FindMeeting_Previews: PreviewProvider {
    static var previews: some View {
        FindMeeting()
    }
}
