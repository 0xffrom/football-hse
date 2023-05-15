//
//  TeamApplicationViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol TeamApplicationViewInput: AnyObject {
    func setLoadingState()
    func removeLoadingState()
    func showAlert()
}

protocol TeamApplicationViewOutput: AnyObject {
    func openChat()
}
