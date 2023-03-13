//
//  AuthorizationCodeEnteringPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

final class AuthorizationCodeEnteringPresenter {

    // MARK: Public Properties

    weak var view: AuthorizationCodeEnteringViewInput?
    weak var moduleOutput: AuthorizationCodeEnteringModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: AuthorizationCodeEnteringInteractorInput
    private var codeInputIsValid: Bool = false


    // MARK: Lifecycle

    init(interactor: AuthorizationCodeEnteringInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - AuthorizationCodeEnteringModuleInput

extension AuthorizationCodeEnteringPresenter: AuthorizationCodeEnteringModuleInput {}

// MARK: - AuthorizationCodeEnteringInteractorOutput

extension AuthorizationCodeEnteringPresenter: AuthorizationCodeEnteringInteractorOutput {}

// MARK: - AuthorizationCodeEnteringViewOutput

extension AuthorizationCodeEnteringPresenter: AuthorizationCodeEnteringViewOutput {

    func viewDidStartEditiong() {
        view?.setNormalState()
    }

    func viewDidEndEditing() {
        guard let view = view else { return } // TODO: Log error

        codeInputIsValid = view.validate()
    }

    func viewDidTapActionButton(with input: String?) {
        guard codeInputIsValid else {
            view?.setErrorState()
            return
        }
        moduleOutput?.moduleWantsToGoToTheNextStep(self)
    }
}
