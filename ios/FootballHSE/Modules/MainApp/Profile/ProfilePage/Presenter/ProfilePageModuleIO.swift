//
//  ProfilePageModuleIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

public protocol ProfilePageModuleInput: AnyObject {
    
}

public protocol ProfilePageModuleOutput: AnyObject {
    func openEditProfile()
    func exit()
}
