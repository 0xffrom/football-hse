//
//  AuthorizationPhoneEnteringViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class AuthorizationPhoneEnteringViewController: UIViewController {
    
    // MARK: Private data structures

    private enum Constants {
        static let phoneNumberTextFieldBottomInset = UIScreen.main.bounds.height / 2 - 96
        static let textFieldsBottomInsetWithKeyboard: CGFloat = 19
        static let textFieldCornerRadius: CGFloat = 14
    }
    
    // MARK: Outlets
    
    @IBOutlet weak var nextButton: HSEMainButton!
    @IBOutlet weak var phoneNumberTextField: HSESmartTextFieldView!

    // MARK: Public Properties

    var output: AuthorizationPhoneEnteringViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        hideKeyboardWhenTappedAround()
        setupView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        addObservers()
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)

        removeObservers()
    }

    // MARK: Private

    private func setupView() {
        setupTextField()
        setupNextButton()
    }

    private func setupTextField() {
        phoneNumberTextField.snp.makeConstraints { make in
            make.bottom.equalTo(view.snp.bottom).inset(Constants.phoneNumberTextFieldBottomInset)
        }
        phoneNumberTextField.cornerRadius = Constants.textFieldCornerRadius
        phoneNumberTextField.keyboardType = .phonePad
        phoneNumberTextField.placeholder = "Номер телефона"
        phoneNumberTextField.errorMessage = "Некорректный номер телефона"
        phoneNumberTextField.configureRestrictions(
            mask:  "+7 ([000]) [000] [00] [00]"
        )
    }

    private func setupNextButton() {
        nextButton.addTarget(self, action: #selector(goNext), for: .touchUpInside)
    }

    // MARK: Actions

    @objc private func goNext(sender: UIButton) {
        output?.viewDidTapActionButton(with: phoneNumberTextField.inputValueWithoutMask)
    }
}

// MARK: - AuthorizationPhoneEnteringViewInput

extension AuthorizationPhoneEnteringViewController: AuthorizationPhoneEnteringViewInput {

    func validate() -> Bool {
        phoneNumberTextField.validate()
    }

    func setNormalState() {
        phoneNumberTextField.validationState = .normal
    }

    func setErrorState() {
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let strongSelf = self else { return }
            strongSelf.phoneNumberTextField.validationState = .error
        }
    }
}

// MARK: - Keyboard Observing

extension AuthorizationPhoneEnteringViewController {

    // MARK: Private

    private func addObservers() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.keyboardWillShow(notification:)),
            name: UIResponder.keyboardWillShowNotification,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.keyboardWillHide(notification:)),
            name: UIResponder.keyboardWillHideNotification,
            object: nil
        )
    }

    private func removeObservers() {
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
    }

    // MARK: Actions

    @objc private func keyboardWillShow(notification: NSNotification) {
        output?.viewDidStartEditiong()

        if let infoKey = notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey],
           let rawFrame = (infoKey as AnyObject).cgRectValue {

            let keyboardHeight = view.convert(rawFrame, from: nil).size.height
            let newBottomInset = keyboardHeight + Constants.textFieldsBottomInsetWithKeyboard

            UIView.animate(withDuration: 0.3) { [weak self] in
                guard let strongSelf = self else { return }

                strongSelf.phoneNumberTextField.snp.updateConstraints { make in
                    make.bottom.equalTo(strongSelf.view.snp.bottom).inset(newBottomInset)
                }

                strongSelf.view.layoutIfNeeded()
            }
        }
    }

    @objc private func keyboardWillHide(notification: NSNotification) {
        output?.viewDidEndEditing()

        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let strongSelf = self else { return }

            strongSelf.phoneNumberTextField.snp.updateConstraints { make in
                make.bottom.equalTo(strongSelf.view.snp.bottom).inset(Constants.phoneNumberTextFieldBottomInset)
            }

            strongSelf.view.layoutIfNeeded()
        }
    }
}
