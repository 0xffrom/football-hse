//
//  EditProfilePageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol EditProfilePageViewInput: AnyObject {
    func setupData(_ data: EditProfilePageViewController.DisplayData)
}

protocol EditProfilePageViewOutput: AnyObject {
    func viewDidLoad()
    func save()
}
