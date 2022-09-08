//
//  TimezoneView.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 8/30/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TimezoneView: View {
    // timezoneItems object passed in from ContentView
    @EnvironmentObject private var timezoneItems: TimezoneItems

    // create an instance of timezoneHelper
    private var timezoneHelper = TimeZoneHelperImpl()

    // get the current date
    @State private var currentDate = Date()

    // create a timer to update every second
    let timer = Timer.publish(every: 1000, on: .main, in: .common).autoconnect()

    // state variable on whether to show the time zone dialog
    @State private var showTimezoneDialog = false
    
    var body: some View {
        // A NavigationView allows you to display new screens with a title and will animate the view
        NavigationView {
            // vertical stack like Column in JC
            VStack {
                // Call the TimeCard class to show the time zone in a nice card format. Use the short and long DateFormatter extensions from the Utils class.
                TimeCard(
                    timezone: timezoneHelper.currentTimeZone(),
                    time: DateFormatter.short.string(from: currentDate),
                    date: DateFormatter.long.string(from: currentDate)
                )
                Spacer()
                // create list of items
                List {
                    // Create an array of selected time zones, and create a card for each one
                    ForEach(Array(timezoneItems.selectedTimezones), id: \.self) { timezone in
                        
                        // Show the time zone in a nice time card. Use a custom list modifier to remove the row separator and insets. (See ListModifier.swift.)
                        NumberTimeCard(timezone: timezone, time: timezoneHelper.getTime(timezoneId: timezone), hours: "\(timezoneHelper.hoursFromTimeZone(otherTimeZoneId: timezone)) hours from local", date: timezoneHelper.getDate(timezoneId: timezone))
                            .withListModifier()
                        
                    } // for each
                    // Add the ability to swipe to delete. You’ll define the deleteItems method later.
                    .onDelete(perform: deleteItems)
                } // list
                // Make the list style plain
                .listStyle(.plain)
                Spacer()
            } // VSTAck
            // Use your timer. Every time the timer changes, update the date, which will then update the other elements
            .onReceive(timer) { input in
                currentDate = input
            }
            .navigationTitle("World Clocks")
            // Add a Toolbar item to the NavigationView
            .toolbar {
                // Place it on the trailing edge (right side for languages that read left to right)
                ToolbarItem(placement: .navigationBarTrailing) {
                    // Create a Button with a plus sign that will set the showTimezoneDialog variable to true.
                    Button(action: {
                        showTimezoneDialog = true
                    }) {
                        Image(systemName: "plus")
                            .frame(alignment: .trailing)
                            .foregroundColor(.black)
                    }
                } // ToolbarItem
            } // toolbar
        }.fullScreenCover(isPresented: $showTimezoneDialog) {
            TimezoneDialog()
              .environmentObject(timezoneItems)
          }
    }
    
    func deleteItems(at offsets: IndexSet) {
        let timezoneArray = Array(timezoneItems.selectedTimezones)
        for index in offsets {
          let element = timezoneArray[index]
          timezoneItems.selectedTimezones.remove(element)
        }
    }
}

struct TimezoneView_Previews: PreviewProvider {
    static var previews: some View {
        TimezoneView()
    }
}
