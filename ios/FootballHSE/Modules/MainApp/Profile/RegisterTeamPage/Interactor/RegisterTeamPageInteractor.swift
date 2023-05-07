//
//  RegisterTeamPageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

final class RegisterTeamPageInteractor {

    // MARK: Public Properties

    weak var output: RegisterTeamPageInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    private func editPhoto(_ image: UIImage?, _ teamId: Int, _ completion: @escaping (Result<Data?, Error>) -> Void) {
        if image == nil {
            completion(.success(nil))
            return
        }

        let config = RequestConfig(
            request: EditTeamPhotoTarget(id: teamId)
        )

        networkService.uploadImage(image: image, urlRequest: config.request.urlRequest, competionHandler: { result in
            switch result {
            case let .success(data):
                CurrentTeamConfig.shared.photo = image
                completion(.success(data))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }
}

// MARK: - RegisterTeamPageInteractorInput

extension RegisterTeamPageInteractor: RegisterTeamPageInteractorInput {

    func createTeam(with teamInfo: CreateTeamModel, image: UIImage?, completion: @escaping (Result<Data?, Error>) -> Void) {

        let config = RequestConfigWithParser(request: CreateTeamTarget(team: teamInfo), parser: TeamParser())

        networkService.sendPostRequestWithParser(config: config) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let team):
                CurrentTeamConfig.shared.name = teamInfo.name
                CurrentTeamConfig.shared.about = teamInfo.about
                self.editPhoto(image, team.id, completion)
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }
}
