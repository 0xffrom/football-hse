//
//  AuthorizationPhoneEnteringPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class AuthorizationPhoneEnteringPresenter {

    // MARK: Public Properties

    weak var view: AuthorizationPhoneEnteringViewInput?
    weak var moduleOutput: AuthorizationPhoneEnteringModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: AuthorizationPhoneEnteringInteractorInput
    private var phoneNumberIsValid: Bool = false
    private var phoneNumber: String?


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
        if phoneNumberIsValid {
            phoneNumber = view.getPhoneNumber()
        }
    }

    func viewDidTapActionButton(with input: String?) {
        guard let phoneNumber = phoneNumber, phoneNumberIsValid else {
            view?.setErrorState()
            return
        }

        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            self.view?.setLoadingState()
        }

        interactor.authorize(with: phoneNumber, completion: { [weak self] result in
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
        })
    }
}
