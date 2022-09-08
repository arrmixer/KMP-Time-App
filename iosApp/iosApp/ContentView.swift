import SwiftUI
import shared

struct ContentView: View {
    @StateObject private var timezoneItems = TimezoneItems()
    var body: some View {
        // Create TabView
        TabView {
            // first tab will be a TimeZoneView
            TimezoneView()
            // Apply the tabItem with a system network icon and the word Time Zones
                .tabItem {
                    Label("Time Zone", systemImage: "network")
                }
            // he second tab will be the FindMeeting
            FindMeeting()
              .tabItem {
                Label("Find Meeting", systemImage: "clock")
              }
        }
        .accentColor(Color.white)
        // Set the timezoneItems object as an environmentObject
        .environmentObject(timezoneItems)
        }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
