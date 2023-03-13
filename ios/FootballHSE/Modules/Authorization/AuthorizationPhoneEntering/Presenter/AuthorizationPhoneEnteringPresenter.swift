//
//  AuthorizationPhoneEnteringPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

final class AuthorizationPhoneEnteringPresenter {

    // MARK: Public Properties

    weak var view: AuthorizationPhoneEnteringViewInput?
    var moduleOutput: AuthorizationPhoneEnteringModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: AuthorizationPhoneEnteringInteractorInput
    private var phoneNumberIsValid: Bool = false


    // MARK: Lifecycle

    init(interactor: AuthorizationPhoneEnteringInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - AuthorizationPhoneEnteringModuleInput

extension AuthorizationPhoneEnteringPresenter: AuthorizationPhoneEnteringModuleInput {}

// MARK: - AuthorizationPhoneEnteringInteractorOutput

extension AuthorizationPhoneEnteringPresenter: AuthorizationPhoneEnteringInteractorOutput {}

// MARK: - AuthorizationPhoneEnteringViewOutput

extension AuthorizationPhoneEnteringPresenter: AuthorizationPhoneEnteringViewOutput {
    func viewDidStartEditiong() {
        guard let view = view else { return } // TODO: Log error
        view.setNormalState()
    }

    func viewDidEndEditing() {
        guard let view = view else { return } // TODO: Log error
        phoneNumberIsValid = view.validate()
    }

    func viewDidTapActionButton(with input: String?) {
        guard phoneNumberIsValid else {
            view?.setErrorState()
            return
        }
        moduleOutput?.moduleWantsToGoToTheNextStep(self)
    }
}
