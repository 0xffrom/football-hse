//
//  PlayersApplicationViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol PlayersApplicationViewInput: AnyObject {
    func setLoadingState()
    func removeLoadingState()
    func showAlert()
}

protocol PlayersApplicationViewOutput: AnyObject {
    func openChat()
}
