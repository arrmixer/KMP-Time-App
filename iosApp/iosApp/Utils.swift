//
//  Utils.swift
//  iosApp
//
//  Created by Angel  Rodriguez on 8/29/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension DateFormatter {
    static let short: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        return formatter
    }()
    static let long: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .long
        formatter.timeStyle = .none
        return formatter
    }()
}
