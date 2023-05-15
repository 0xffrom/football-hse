//
//  AuthorizationCodeEnteringPresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import Foundation

final class AuthorizationCodeEnteringPresenter {

    // MARK: Public Properties

    weak var view: AuthorizationCodeEnteringViewInput?
    weak var moduleOutput: AuthorizationCodeEnteringModuleOutput?

    // MARK: Private Properties

    private let interactor: AuthorizationCodeEnteringInteractorInput
    private var codeInputIsValid: Bool = false
    private var code: String?

    // MARK: Lifecycle

    init(interactor: AuthorizationCodeEnteringInteractorInput) {
        self.interactor = interactor
    }
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
        if codeInputIsValid {
            code = view.getCode()
        }
    }

    private func getUserInfo() {
        interactor.getCurrentUser(completion: { [weak self] result in
            guard let self = self, let view = self.view else { return }
            switch result {
            case .success(_):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self else { return }
                    view.removeLoadingState()
                    self.moduleOutput?.moduleWantsToGoToMainApp(self)
                }
            case .failure(_):
                DispatchQueue.main.async { [weak self] in
                    self?.view?.removeLoadingState()
                    self?.view?.showNetworkErrorAlert()
                }
            }
        })
    }

    func viewDidTapActionButton(with input: String?) {
        guard let code = code, codeInputIsValid else {
            view?.setErrorState()
            return
        }

        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            self.view?.setLoadingState()
        }

        interactor.sendCode(code: code, completion: { [weak self] result in
            guard let self = self else { return }
            switch result {
            case .success(let info):
                DispatchQueue.main.async { [weak self] in
                    guard let self = self else { return }
                    if info.isRegistered {
                        self.getUserInfo()
                    } else {
                        self.moduleOutput?.moduleWantsToGoToRegistration(self)
                    }
                }
            case .failure(let error):
                DispatchQueue.main.async { [weak self] in
                    self?.view?.removeLoadingState()
                }
                if let networkError = error as? NetworkError, networkError == .wrongParams {
                    DispatchQueue.main.async { [weak self] in
                        self?.view?.showWrongCodeAlert()
                    }
                } else {
                    DispatchQueue.main.async { [weak self] in
                        self?.view?.showNetworkErrorAlert()
                    }
                }
            }
        })
    }
}
