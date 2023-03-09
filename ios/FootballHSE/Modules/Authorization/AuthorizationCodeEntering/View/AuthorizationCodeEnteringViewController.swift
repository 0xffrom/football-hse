//
//  AuthorizationCodeEnteringViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class AuthorizationCodeEnteringViewController: UIViewController {
    
    // MARK: Private data structures

    private enum Constants {
        static let textFieldsBottomInset = UIScreen.main.bounds.height / 2 - 96
        static let textFieldsBottomInsetWithKeyboard: CGFloat = 19
        static let textFieldsCornerRadius: CGFloat = 12
    }
    
    // MARK: Outlets

    @IBOutlet weak var codeStackView: UIStackView!
    @IBOutlet weak var codeTextField1: HSESmartTextFieldView!
    @IBOutlet weak var codeTextField2: HSESmartTextFieldView!
    @IBOutlet weak var codeTextField3: HSESmartTextFieldView!
    @IBOutlet weak var codeTextField4: HSESmartTextFieldView!

    @IBOutlet weak var nextButton: HSEMainButton!

    @IBOutlet weak var errorLabel: UILabel!

    // MARK: Public Properties

    var output: AuthorizationCodeEnteringViewOutput?

    // MARK: Private Properties

    private lazy var codeFields = [codeTextField1, codeTextField2, codeTextField3, codeTextField4]

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
        setupCodeInputView()
        setupErrorLabel()
        setupNextButton()
    }

    private func setupCodeInputView() {
        codeStackView.snp.makeConstraints { make in
            make.bottom.equalTo(view.snp.bottom).inset(Constants.textFieldsBottomInset)
        }
        setupTextFields()
    }

    private func setupTextFields() {
        codeFields.forEach { field in
            guard let field = field else { return }
            // TODO: Log error
            field.cornerRadius = Constants.textFieldsCornerRadius
            field.keyboardType = .numberPad
            field.verticalInset = 16
            field.horizontalInset = 18
            field.font = .systemFont(ofSize: 30, weight: .medium)
            field.configureRestrictions(mask: "[0]")
        }
    }

    private func setupErrorLabel() {
        errorLabel.snp.makeConstraints { make in
            make.height.equalTo(0)
            make.top.equalTo(codeTextField1.snp.bottom).inset(0)
        }
    }

    private func setupNextButton() {
        nextButton.addTarget(self, action: #selector(goNext), for: .touchUpInside)
    }

    private func getInputCode() -> String {
        var codeString = ""
        codeFields.forEach { field in
            if let number = field?.inputValueWithoutMask {
                codeString += String(number)
            }
        }
        return codeString
    }

    // MARK: Actions

    @objc private func goNext(sender: UIButton) {
        let code = getInputCode()
        output?.viewDidTapActionButton(with: code)
    }
}

// MARK: - AuthorizationCodeEnteringViewInput

extension AuthorizationCodeEnteringViewController: AuthorizationCodeEnteringViewInput {

    func validate() -> Bool {
        var valid = false
        codeFields.forEach { field in
            valid = field?.validate() ?? false // TODO: Log error
        }
        return valid
    }

    func setErrorState() {
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let strongSelf = self else { return }

            strongSelf.nextButton.isHidden = true

            strongSelf.errorLabel.isHidden = false
            strongSelf.errorLabel.snp.updateConstraints { make in
                make.height.equalTo(22)
                make.top.equalTo(strongSelf.codeTextField1.snp.bottom).inset(-36)
            }

            strongSelf.codeFields.forEach { field in
                field?.validationState = .error
            }
        }
    }

    func setNormalState() {
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let strongSelf = self else { return }

            strongSelf.errorLabel.isHidden = true
            strongSelf.errorLabel.snp.updateConstraints { make in
                make.height.equalTo(0)
                make.top.equalTo(strongSelf.codeTextField1.snp.bottom).inset(0)
            }

            strongSelf.nextButton.isHidden = false
            strongSelf.codeFields.forEach { field in
                field?.validationState = .normal
            }
        }
    }
}

// MARK: - Keyboard Observing

extension AuthorizationCodeEnteringViewController {

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
            selector: #selector(self.keyboardDidShow(notification:)),
            name: UIResponder.keyboardDidShowNotification,
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
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardDidShowNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
    }

    // MARK: Actions

    @objc private func keyboardWillShow(notification: NSNotification) {
        if let infoKey = notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey],
           let rawFrame = (infoKey as AnyObject).cgRectValue {

            let keyboardHeight = view.convert(rawFrame, from: nil).size.height
            let newBottomInset = keyboardHeight + Constants.textFieldsBottomInsetWithKeyboard

            UIView.animate(withDuration: 0.3) { [weak self] in
                guard let strongSelf = self else { return }

                strongSelf.codeStackView.snp.updateConstraints { make in
                    make.bottom.equalTo(strongSelf.view.snp.bottom).inset(newBottomInset)
                }

                strongSelf.view.layoutIfNeeded()
            }
        }
    }

    @objc private func keyboardDidShow(notification: NSNotification) {
        output?.viewDidStartEditiong()
    }

    @objc private func keyboardWillHide(notification: NSNotification) {
        output?.viewDidEndEditing()

        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let strongSelf = self else { return }

            strongSelf.codeStackView.snp.updateConstraints { make in
                make.bottom.equalTo(strongSelf.view.snp.bottom).inset(Constants.textFieldsBottomInset)
            }

            strongSelf.view.layoutIfNeeded()
        }
    }
}
