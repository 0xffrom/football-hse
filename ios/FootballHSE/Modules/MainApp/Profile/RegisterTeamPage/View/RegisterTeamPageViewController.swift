//
//  RegisterTeamPageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class RegisterTeamPageViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var nameTextField: HSESmartTextFieldView!
    @IBOutlet weak var infoTextField: HSESmartTextFieldView!

    @IBOutlet weak var saveButton: HSEMainButton!

    // MARK: Public Properties

    var output: RegisterTeamPageViewOutput?

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
        setupTextFields()
        navigationItem.title = "Регистрация команды";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes
        saveButton.addTarget(self, action: #selector(save), for: .touchUpInside)
    }

    private func setupTextFields() {
        nameTextField.cornerRadius = 12
        nameTextField.horizontalInset = 16
        nameTextField.verticalInset = 12
        nameTextField.hightOfTextField = 48
        nameTextField.autocorrectionType = .no
        nameTextField.descriptionLabelText = "НАЗВАНИЕ КОМАНДЫ"
        nameTextField.placeholder = "Введите название"

        infoTextField.cornerRadius = 12
        infoTextField.horizontalInset = 16
        infoTextField.verticalInset = 12
        infoTextField.hightOfTextField = 48
        infoTextField.autocorrectionType = .no
        infoTextField.descriptionLabelText = "ИНФОРМАЦИЯ О КОМАНДЕ"
        infoTextField.placeholder = "Расскажите о вашей команде"
    }

    @objc private func save(sender: UIButton) {
        output?.save()
    }
}

// MARK: - Keyboard Observing

extension RegisterTeamPageViewController {

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
        // output?.viewDidStartEditiong()
    }

    @objc private func keyboardWillHide(notification: NSNotification) {
        // output?.viewDidEndEditing()
    }
}

// MARK: - RegisterTeamPageViewInput

extension RegisterTeamPageViewController: RegisterTeamPageViewInput {

}
