//
//  AuthorizationPhoneEnteringViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol AuthorizationPhoneEnteringViewInput: AnyObject {
    func validate() -> Bool
    func setNormalState()
    func setErrorState()
}

protocol AuthorizationPhoneEnteringViewOutput: AnyObject {
    func viewDidStartEditiong()
    func viewDidEndEditing()
    func viewDidTapActionButton(with input: String?)
}
