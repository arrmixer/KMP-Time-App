//
//  ListModifier.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 8/29/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ListModifier: ViewModifier {

    func body(content: Content) -> some View {
        if #available(iOS 15.0, *) {
            content
                .listRowInsets(.init())
                .listRowSeparator(.hidden)
        } else {
            content
        }
    }
}

extension View {
    func withListModifier() -> some View {
        modifier(ListModifier())
    }
}
