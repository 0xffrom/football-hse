//
//  MyApplicationsPageInteractorIO.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation


protocol MyApplicationsPageInteractorInput: AnyObject {
    func getApplications(completion: @escaping (Result<[ProfileApplicationDisplayModel], Error>) -> Void)
    func deleteApplication(with id: Int, completion: @escaping (Result<Data?, Error>) -> Void)
}

protocol MyApplicationsPageInteractorOutput: AnyObject {
    
}
