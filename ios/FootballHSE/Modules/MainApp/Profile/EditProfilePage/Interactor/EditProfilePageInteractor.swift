//
//  EditProfilePageInteractor.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

final class EditProfilePageInteractor {

    // MARK: Public Properties

    weak var output: EditProfilePageInteractorOutput?

    // MARK: Private Properties

    private let networkService: INetworkService

    // MARK: Lifecycle

    init(networkService: INetworkService) {
        self.networkService = networkService
    }

    // MARK: Private

    private func isMainInfoChanged(newProfileInfo: EditProfileModel) -> Bool {
        newProfileInfo.name != CurrentUserConfig.shared.name ||
        newProfileInfo.footballExperience != CurrentUserConfig.shared.footballExperience ||
        newProfileInfo.tournamentExperience != CurrentUserConfig.shared.tournamentExperience ||
        newProfileInfo.contact != CurrentUserConfig.shared.contact ||
        newProfileInfo.about != CurrentUserConfig.shared.about
    }

    private func editPhoto(_ image: UIImage?, _ completion: @escaping (Result<Data?, Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }
        let config = RequestConfig(
            request: EditProfilePhotoTarget(phoneNumber: phoneNumber)
        )

        networkService.uploadImage(image: image, urlRequest: config.request.urlRequest, competionHandler: { result in
            switch result {
            case let .success(data):
                completion(.success(data))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }

    private func editMainInfo(_ newInfo: EditProfileModel, _ completion: @escaping (Result<Data?, Error>) -> Void) {
        getProfileInfo { [weak self] result in
            guard let self else { return }

            switch result {
            case .success(let data):
                guard let data else { return }
                self.editMainInfo(newInfo: newInfo, oldInfo: data, completion: completion)
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    private func editMainInfo(newInfo: EditProfileModel,
                              oldInfo: ProfileResponseModel,
                              completion: @escaping (Result<Data?, Error>) -> Void) {
        let config = RequestConfig(
            request: EditProfileTarget(newInfo: newInfo,
                                       oldInfo: oldInfo)
        )

        self.networkService.sendPostRequest(config: config, competionHandler: { result in
            switch result {
            case let .success(data):
                CurrentUserConfig.shared.name = newInfo.name
                CurrentUserConfig.shared.footballExperience = newInfo.footballExperience
                CurrentUserConfig.shared.tournamentExperience = newInfo.tournamentExperience
                CurrentUserConfig.shared.contact = newInfo.contact
                CurrentUserConfig.shared.about = newInfo.about
                CurrentUserConfig.shared.photo = newInfo.photo

                completion(.success(data))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }

    private func getProfileInfo(_ completion: @escaping (Result<ProfileResponseModel?, Error>) -> Void) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }
        let config = RequestConfigWithParser(
            request: GetProfileTarget(phoneNumber: phoneNumber),
            parser: ProfileParser()
        )
        networkService.sendGetRequest(config: config, competionHandler: { result in
            switch result {
            case let .success(data):
                CurrentUserConfig.shared.name = data.name
                CurrentUserConfig.shared.footballExperience = data.footballExperience
                CurrentUserConfig.shared.tournamentExperience = data.tournamentExperience
                CurrentUserConfig.shared.contact = data.contact
                CurrentUserConfig.shared.about = data.about
                CurrentUserConfig.shared.photo = data.photo

                completion(.success(data))
            case let .failure(error):
                completion(.failure(error))
            }
        })
    }
}

// MARK: - EditProfilePageInteractorInput

extension EditProfilePageInteractor: EditProfilePageInteractorInput {

    // MARK: Public

    func editProfileInfo(newProfileInfo: EditProfileModel, image: UIImage? = nil, completion: @escaping (Result<Data?, Error>) -> Void) {
        let isPhotoChanged = image != nil
        let isMainInfoChanged = isMainInfoChanged(newProfileInfo: newProfileInfo)

        if isPhotoChanged && isMainInfoChanged {
            editPhoto(image) { [weak self] _ in
                guard let self else { return }
                self.editMainInfo(newProfileInfo, completion)
            }
        } else if isMainInfoChanged {
            editMainInfo(newProfileInfo, completion)
        } else {
            editPhoto(image) { [weak self] _ in
                guard let self else { return }
                self.getProfileInfo() { result in
                    switch result {
                    case .success(_):
                        completion(.success(nil))
                    case .failure(let error):
                        completion(.failure(error))
                    }
                }
            }
        }
    }
}
