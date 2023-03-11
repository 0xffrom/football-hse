//
//  RegistrationViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class RegistrationViewController: UIViewController {
    
    // MARK: Private data structures

    private enum Constants {
        static let textFieldCornerRadius: CGFloat = 12
    }
    
    // MARK: Outlets

    @IBOutlet weak var nameTextField: HSESmartTextFieldView!

    @IBOutlet weak var studentButton: HSESelectableViewButton!
    @IBOutlet weak var graduateButton: HSESelectableViewButton!
    @IBOutlet weak var employeeButton: HSESelectableViewButton!
    @IBOutlet weak var legionnaireButton: HSESelectableViewButton!

    @IBOutlet weak var noRoleErrorLabel: UILabel!

    @IBOutlet weak var nextButton: HSEMainButton!

    // MARK: Public Properties

    var output: RegistrationViewOutput?

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
        setupNoRoleErrorLabel()
        setupActions()
    }

    private func setupTextField() {
        nameTextField.cornerRadius = Constants.textFieldCornerRadius
        nameTextField.horizontalInset = 16
        nameTextField.verticalInset = 12
        nameTextField.hightOfTextField = 48

        nameTextField.textContentType = .name
        nameTextField.autocapitalizationType = .words
        nameTextField.autocorrectionType = .no

        nameTextField.descriptionLabelText = "ФИО"
        nameTextField.placeholder = "Введите ФИО"
        nameTextField.errorMessage = "Некорректное ФИО"

        nameTextField.configureRestrictions(
            minLength: 2,
            maxLength: 41,
            predicteFormat: "[А-Яа-яЁёa-zA-Z]+$" // TODO: поменять
        )
    }

    private func setupNoRoleErrorLabel() {
        noRoleErrorLabel.snp.makeConstraints { make in
            make.height.equalTo(0)
            make.top.equalTo(employeeButton.snp.bottom).offset(0)
        }
    }

    private func setupActions() {
        studentButton.addTarget(self, action: #selector(studentRoleSelected), for: .touchUpInside)
        graduateButton.addTarget(self, action: #selector(graduateRoleSelected), for: .touchUpInside)
        employeeButton.addTarget(self, action: #selector(employeeRoleSelected), for: .touchUpInside)
        legionnaireButton.addTarget(self, action: #selector(legionnaireRoleSelected), for: .touchUpInside)

        nextButton.addTarget(self, action: #selector(goNext), for: .touchUpInside)
    }

    // MARK: Actions

    @objc private func studentRoleSelected(sender: UIButton) {
        output?.viewDidSelectRole(.student)
    }

    @objc private func graduateRoleSelected(sender: UIButton) {
        output?.viewDidSelectRole(.graduate)
    }

    @objc private func employeeRoleSelected(sender: UIButton) {
        output?.viewDidSelectRole(.employee)
    }

    @objc private func legionnaireRoleSelected(sender: UIButton) {
        output?.viewDidSelectRole(.legionnaire)
    }

    @objc private func goNext(sender: UIButton) {
        output?.viewDidTapActionButton(with: nameTextField.inputValueWithoutMask)
    }
}

// MARK: - Keyboard Observing

extension RegistrationViewController {

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
    }

    @objc private func keyboardWillHide(notification: NSNotification) {
        output?.viewDidEndEditing()
    }
}


// MARK: - RegistrationViewInput

extension RegistrationViewController: RegistrationViewInput {

    func validate() -> Bool {
        nameTextField.validate()
    }

    func setNameNormalState() {
        nameTextField.validationState = .normal
    }

    func setNameErrorState() {
        nameTextField.validationState = .error
    }

    func selecteRole(_ role: PlayerRole) {
        switch role {
        case .student:
            studentButton.selectedState = true
        case .graduate:
            graduateButton.selectedState = true
        case .employee:
            employeeButton.selectedState = true
        case .legionnaire:
            legionnaireButton.selectedState = true
        }
    }

    func unselectRole(_ role: PlayerRole) {
        switch role {
        case .student:
            studentButton.selectedState = false
        case .graduate:
            graduateButton.selectedState = false
        case .employee:
            employeeButton.selectedState = false
        case .legionnaire:
            legionnaireButton.selectedState = false
        }
    }

    func setRoleNormalState() {
        UIView.animate(withDuration: 0.4) { [weak self] in
            guard let strongSelf = self else { return }
            strongSelf.noRoleErrorLabel.snp.updateConstraints { make in
                make.height.equalTo(0)
                make.top.equalTo(strongSelf.employeeButton.snp.bottom).offset(0)
            }
        }
    }

    func setNoRoleErrorState() {
        UIView.animate(withDuration: 0.4) { [weak self] in
            guard let strongSelf = self else { return }
            strongSelf.noRoleErrorLabel.snp.updateConstraints { make in
                make.height.equalTo(20)
                make.top.equalTo(strongSelf.employeeButton.snp.bottom).offset(6)
            }
        }
    }
}
