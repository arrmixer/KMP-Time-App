//
//  HourSheet.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 9/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HourSheet: View {
    @Binding var hours: [Int]
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
       // Use a NavigationView to show a toolbar
        NavigationView {
            // Use a VStack for the title
            VStack {
                // Use a List to show each hour
                List {
                    // Use the ForEach view to show a text view for each hour
                    ForEach(hours, id: \.self) {  hour in
                        Text("\(hour)")
                    }
                } // List
            } // VStack
            .navigationTitle("Found Meeting Hours")
            // Show a Toolbar with a Dismiss button
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        presentationMode.wrappedValue.dismiss()
                    }) {
                        Text("Dismiss")
                            .frame(alignment: .trailing)
                            .foregroundColor(.black)
                    }
                } // ToolbarItem
            } // toolbar
        } // NavigationView
    }
}

struct HourSheet_Previews: PreviewProvider {
    static var previews: some View {
        HourSheet(hours: .constant([8,9,10]))
    }
}
