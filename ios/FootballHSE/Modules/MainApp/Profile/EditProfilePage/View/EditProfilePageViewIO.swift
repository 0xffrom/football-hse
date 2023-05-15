//
//  EditProfilePageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

protocol EditProfilePageViewInput: AnyObject {
    func validate() -> Bool
    func setupData(_ data: EditProfileModel)
    func getData() -> EditProfileModel
    func showAlert()
    func setNameNormalState()
    func setNameErrorState()
    func setLoadingState()
    func removeLoadingState()
}

protocol EditProfilePageViewOutput: AnyObject {
    func viewDidLoad()
    func viewDidStartEditiong()
    func viewDidEndEditing()
    func save(withPhoto photo: UIImage?)
    func profilePhotoChanged()
}
