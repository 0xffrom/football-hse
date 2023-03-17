//
//  RegistrationInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//


final class RegistrationInteractor {

    // MARK: Public Properties
    
    weak var output: RegistrationInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService
    private let currentUserConfig: CurrentUserConfig

    // MARK: Lifecycle

    init(
        networkService: INetworkService,
        currentUserConfig: CurrentUserConfig
    ) {
        self.networkService = networkService
        self.currentUserConfig = currentUserConfig
    }
    
    // MARK: Public

}




// MARK: - RegistrationInteractorInput

extension RegistrationInteractor: RegistrationInteractorInput {}
