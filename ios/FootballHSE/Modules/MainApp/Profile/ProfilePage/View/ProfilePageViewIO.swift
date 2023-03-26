//
//  ProfilePageViewIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

protocol ProfilePageViewInput: AnyObject {

}

protocol ProfilePageViewOutput: AnyObject {
    func viewWantToEditProfile()
    func exit()
}
