//
//  EditProfilePageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class EditProfilePageViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var nameTextField: HSESmartTextFieldView!
    @IBOutlet weak var footballExperienceTextField: HSESmartTextFieldView!
    @IBOutlet weak var tournamentExperienceTextField: HSESmartTextFieldView!
    @IBOutlet weak var contactTextField: HSESmartTextFieldView!
    @IBOutlet weak var aboutTextField: HSESmartTextFieldView!

    @IBOutlet weak var saveButton: HSEMainButton!

    // MARK: Public Properties

    var output: EditProfilePageViewOutput?

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
        navigationItem.title = "Редактирование профиля";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes
        saveButton.addTarget(self, action: #selector(save), for: .touchUpInside)
    }

    private func setupTextFields() {
        nameTextField.text = CurrentUserConfig.shared.name
        nameTextField.cornerRadius = 12
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
            predicteFormat: "[А-Яа-яЁёa-zA-Z]+$"
        )

        footballExperienceTextField.text = CurrentUserConfig.shared.footballExperience
        footballExperienceTextField.cornerRadius = 12
        footballExperienceTextField.horizontalInset = 16
        footballExperienceTextField.verticalInset = 12
        footballExperienceTextField.hightOfTextField = 48
        footballExperienceTextField.descriptionLabelText = "ФУТБОЛЬНЫЙ ОПЫТ"
        footballExperienceTextField.placeholder = "Расскажите про свой опыт"

        tournamentExperienceTextField.text = CurrentUserConfig.shared.tournamentExperience
        tournamentExperienceTextField.cornerRadius = 12
        tournamentExperienceTextField.horizontalInset = 16
        tournamentExperienceTextField.verticalInset = 12
        tournamentExperienceTextField.hightOfTextField = 48
        tournamentExperienceTextField.descriptionLabelText = "ОПЫТ В ТУРНИРАХ НИУ ВШЭ"
        tournamentExperienceTextField.placeholder = "В каких турнирах вы играли?"

        contactTextField.text = CurrentUserConfig.shared.contact
        contactTextField.cornerRadius = 12
        contactTextField.horizontalInset = 16
        contactTextField.verticalInset = 12
        contactTextField.hightOfTextField = 48
        contactTextField.descriptionLabelText = "КОНТАКТНАЯ ИНФОРМАЦИЯ"
        contactTextField.placeholder = "Телефон, Telegram..."

        aboutTextField.text = CurrentUserConfig.shared.about
        aboutTextField.cornerRadius = 12
        aboutTextField.horizontalInset = 16
        aboutTextField.verticalInset = 12
        aboutTextField.hightOfTextField = 48
        aboutTextField.descriptionLabelText = "О СЕБЕ"
        aboutTextField.placeholder = "Расскажите о себе"
    }
}

// MARK: - Keyboard Observing

extension EditProfilePageViewController {

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

    @objc private func save(sender: UIButton) {
        CurrentUserConfig.shared.name = nameTextField.text
        CurrentUserConfig.shared.footballExperience = footballExperienceTextField.text
        CurrentUserConfig.shared.tournamentExperience = tournamentExperienceTextField.text
        CurrentUserConfig.shared.contact = contactTextField.text
        CurrentUserConfig.shared.about = aboutTextField.text
        output?.save()
    }
}

// MARK: - EditProfilePageViewInput

extension EditProfilePageViewController: EditProfilePageViewInput {

    func setLoadingState() {
        saveButton.isLoading = true
    }

    func removeLoadingState() {
        saveButton.isLoading = false
    }
}
