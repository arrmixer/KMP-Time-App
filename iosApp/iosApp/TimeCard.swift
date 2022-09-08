//
//  TimeCard.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 8/29/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct TimeCard: View {
  var timezone: String
  var time: String
  var date: String
  var body: some View {
    HStack {
      VStack(alignment: .leading) {
        Text("Your Location")
          .font(Font.caption)
          .foregroundColor(Color.white)
        Spacer().frame(height:8.0)
        Text(timezone)
          .lineLimit(1)
          .font(.system(size: 16.0))
          .foregroundColor(Color.white)
      }
      .padding(.leading, 8).padding(.bottom, 16)
      Spacer()
      VStack(alignment: .trailing) {
        Text(time)
          .font(.system(size: 34.0))
          .foregroundColor(Color.white)
        Spacer().frame(height:8.0)
        Text(date)
          .lineLimit(1)
          .font(.system(size: 12.0))
          .foregroundColor(Color.white)
      }
      .padding(.trailing, 8).padding(.bottom, 16)
    }
    .frame(maxWidth: .infinity, minHeight: 120, alignment: Alignment(horizontal: .leading, vertical: .bottom))
    .background(LinearGradient(gradient: Gradient(colors:
                                                    [
                                                      Color(.sRGB, red: 25/255, green: 117/255, blue: 210/255),
                                                      Color(.sRGB, red: 99/255, green: 164/255, blue: 255/255),
                                                    ]),
                               startPoint: .trailing, endPoint: .leading))
    .cornerRadius(10)
    .overlay(
      RoundedRectangle(cornerRadius: 8)
        .stroke(Color(.sRGB, red: 150/255, green: 150/255, blue: 150/255, opacity: 1.0), lineWidth: 1)
    )
    .padding([.top, .horizontal])
  }
}

struct TimeCard_Previews: PreviewProvider {
  static var previews: some View {
    TimeCard(timezone: "America/Los_Angeles",
             time: "1:39 pm", date: "Saturday, October 16")
  }
}
