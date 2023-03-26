//
//  AuthorizationPhoneEnteringViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol AuthorizationPhoneEnteringViewInput: AnyObject {
    func validate() -> Bool
    func getPhoneNumber() -> String?
    func showAlert()
    func setNormalState()
    func setErrorState()
    func setLoadingState()
    func removeLoadingState()
}

protocol AuthorizationPhoneEnteringViewOutput: AnyObject {
    func viewDidStartEditiong()
    func viewDidEndEditing()
    func viewDidTapActionButton(with input: String?)
}
