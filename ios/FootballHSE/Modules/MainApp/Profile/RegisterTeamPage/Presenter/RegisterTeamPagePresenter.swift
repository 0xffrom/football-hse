//
//  RegisterTeamPagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit

final class RegisterTeamPagePresenter {

    // MARK: Public Properties

    weak var view: RegisterTeamPageViewInput?
    var moduleOutput: RegisterTeamPageModuleOutput?

    private var nameIsValid: Bool = true
    
    // MARK: Private Properties

    private let interactor: RegisterTeamPageInteractorInput

    private var isTeamPhotoChanged = false

    // MARK: Lifecycle

    init(interactor: RegisterTeamPageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - RegisterTeamPageModuleInput

extension RegisterTeamPagePresenter: RegisterTeamPageModuleInput {}

// MARK: - RegisterTeamPageInteractorOutput

extension RegisterTeamPagePresenter: RegisterTeamPageInteractorOutput {}

// MARK: - RegisterTeamPageViewOutput

extension RegisterTeamPagePresenter: RegisterTeamPageViewOutput {

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

        let photoToUpload = isTeamPhotoChanged ? photo : nil

        interactor.createTeam(with: data, image: photoToUpload) { [weak self] result in
            guard let self = self, let view = self.view else { return }
            switch result {
            case .success(_):
                DispatchQueue.main.asyncAfter(deadline: .now() + 1) { [weak self] in
                    guard let self = self else { return }
                    view.removeLoadingState()
                    self.moduleOutput?.backWithTeamRegistered()
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

    func teamPhotoChanged() {
        isTeamPhotoChanged = true
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
