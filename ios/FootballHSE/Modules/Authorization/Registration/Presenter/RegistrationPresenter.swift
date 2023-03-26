//
//  RegistrationPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class RegistrationPresenter {

    // MARK: Public Properties

    weak var view: RegistrationViewInput?
    weak var moduleOutput: RegistrationModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: RegistrationInteractorInput

    private var nameIsValid: Bool = false
    private var name: String? = nil
    private var selectedRole: PlayerRole? = nil

    // MARK: Lifecycle

    init(interactor: RegistrationInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - RegistrationModuleInput

extension RegistrationPresenter: RegistrationModuleInput {}

// MARK: - RegistrationInteractorOutput

extension RegistrationPresenter: RegistrationInteractorOutput {}

// MARK: - RegistrationViewOutput

extension RegistrationPresenter: RegistrationViewOutput {
    func viewDidSelectRole(_ role: PlayerRole) {
        if let currentRole = selectedRole {
            view?.unselectRole(currentRole)
        } else {
            view?.setRoleNormalState()
        }
        selectedRole = role
        view?.selecteRole(role)
    }

    func viewDidStartEditiong() {
        guard let view = view else { return } // TODO: Log error
        view.setNameNormalState()
    }

    func viewDidEndEditing() {
        guard let view = view else { return } // TODO: Log error
        nameIsValid = view.validate()
        name = view.getName()
    }

    func viewDidTapActionButton(with input: String?) {
        guard let view = view else { return }

        if !nameIsValid {
            view.setNameErrorState()
        }

        if selectedRole == nil {
            view.setNoRoleErrorState()
        }

        guard nameIsValid && selectedRole != nil else { return }
        guard let name = name, let roleId = selectedRole?.rawValue else { return }

        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            self.view?.setLoadingState()
        }

        interactor.registerUser(name: name, roleId: roleId) { [weak self] result in
            guard let self = self, let view = self.view else { return }
            switch result {
            case .success(_):
                DispatchQueue.main.asyncAfter(deadline: .now() + 1) { [weak self] in
                    guard let self = self else { return }
                    view.removeLoadingState()
                    self.moduleOutput?.moduleWantsToGoToTheNextStep(self)
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
}
