//
//  KeyboardInfo.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 09.05.2023.
//

import UIKit

struct KeyboardInfo {
    var frameBegin: CGRect?
    var frameEnd: CGRect?
}

extension KeyboardInfo {
    init?(_ notification: NSNotification) {
        guard notification.name == UIResponder.keyboardWillShowNotification ||
                notification.name == UIResponder.keyboardWillChangeFrameNotification else { return nil }
        if let userInfo = notification.userInfo {
            frameBegin = userInfo[UIWindow.keyboardFrameBeginUserInfoKey] as? CGRect
            frameEnd = userInfo[UIWindow.keyboardFrameEndUserInfoKey] as? CGRect
        }
    }
}
