//
//  AuthorizationCodeEnteringViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol AuthorizationCodeEnteringViewInput: AnyObject {
    func validate() -> Bool
    func setErrorState()
    func setNormalState()
}

protocol AuthorizationCodeEnteringViewOutput: AnyObject {
    func viewDidStartEditiong()
    func viewDidEndEditing()
    func viewDidTapActionButton(with input: String?)
}
