//
//  EditProfilePageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

protocol EditProfilePageInteractorInput: AnyObject {
    func editProfileInfo(newProfileInfo: EditProfileModel, image: UIImage?, completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol EditProfilePageInteractorOutput: AnyObject {}
