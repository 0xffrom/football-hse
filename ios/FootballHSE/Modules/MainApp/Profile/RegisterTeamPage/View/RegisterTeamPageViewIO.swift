//
//  RegisterTeamPageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

protocol RegisterTeamPageViewInput: AnyObject {
    func validate() -> Bool
    func getData() -> CreateTeamModel
    func showAlert()
    func setNameNormalState()
    func setNameErrorState()
    func setLoadingState()
    func removeLoadingState()
}

protocol RegisterTeamPageViewOutput: AnyObject {
    func viewDidStartEditiong()
    func viewDidEndEditing()
    func save(withPhoto photo: UIImage?)
    func teamPhotoChanged()
}
