//
//  RegistrationPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

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
        if !nameIsValid {
            view?.setNameErrorState()
        }

        if selectedRole == nil {
            view?.setNoRoleErrorState()
        }

        guard nameIsValid && selectedRole != nil else { return }

        moduleOutput?.moduleWantsToGoToTheNextStep(self)
    }
}
