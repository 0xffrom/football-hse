//
//  EditProfilePagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

final class EditProfilePagePresenter {

    // MARK: Public Properties

    weak var view: EditProfilePageViewInput?
    var moduleOutput: EditProfilePageModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: EditProfilePageInteractorInput

    private var nameIsValid: Bool = true
    private var isProfilePhotoChanged = false

    // MARK: Lifecycle

    init(interactor: EditProfilePageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - EditProfilePageModuleInput

extension EditProfilePagePresenter: EditProfilePageModuleInput {}

// MARK: - EditProfilePageInteractorOutput

extension EditProfilePagePresenter: EditProfilePageInteractorOutput {}

// MARK: - EditProfilePageViewOutput

extension EditProfilePagePresenter: EditProfilePageViewOutput {

    func viewDidLoad() {
        let data = EditProfileModel(
            photo: CurrentUserConfig.shared.photo,
            name: CurrentUserConfig.shared.name,
            footballExperience: CurrentUserConfig.shared.footballExperience,
            tournamentExperience: CurrentUserConfig.shared.tournamentExperience,
            contact: CurrentUserConfig.shared.contact,
            about: CurrentUserConfig.shared.about
        )
        view?.setupData(data)
    }

    func save(withPhoto photo: UIImage?) {
        guard let data = view?.getData() else {
            return
        }
        guard data.name != nil, nameIsValid else {
            view?.setNameErrorState()
            return
        }

        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            self.view?.setLoadingState()
        }

        let photoToUpload = isProfilePhotoChanged ? photo : nil

        interactor.editProfileInfo(newProfileInfo: data, image: photoToUpload) { [weak self] result in
            guard let self = self, let view = self.view else { return }
            switch result {
            case .success(_):
                DispatchQueue.main.asyncAfter(deadline: .now() + 1) { [weak self] in
                    guard let self = self else { return }
                    view.removeLoadingState()
                    self.moduleOutput?.back()
                }
            case .failure(_):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self, let view = self.view else { return }
                    view.removeLoadingState()
                    view.showAlert()
                }
            }
        }
    }

    func profilePhotoChanged() {
        isProfilePhotoChanged = true
    }

    func viewDidStartEditiong() {
        guard let view = view else { return } // TODO: Log error
        view.setNameNormalState()
    }

    func viewDidEndEditing() {
        guard let view = view else { return } // TODO: Log error
        nameIsValid = view.validate()
    }
}
