//
//  RegistrationViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol RegistrationViewInput: AnyObject {
    func validate() -> Bool

    func setNameNormalState()
    func setNameErrorState()

    func setRoleNormalState()
    func setNoRoleErrorState()

    func selecteRole(_ role: PlayerRole)
    func unselectRole(_ role: PlayerRole)
}

protocol RegistrationViewOutput: AnyObject {
    func viewDidStartEditiong()
    func viewDidEndEditing()
    func viewDidSelectRole(_ role: PlayerRole)
    func viewDidTapActionButton(with input: String?)
}
