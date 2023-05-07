//
//  EditProfilePageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class EditProfilePageViewController: UIViewController {

    // MARK: Private data structures

    private enum Constants {
        static let profileImageCornerRadius: CGFloat = 16
        static let initialImage = R.image.addPhotoImage()
    }

    // MARK: Outlets

    @IBOutlet weak var profileImage: UIImageView!

    @IBOutlet weak var nameTextField: HSESmartTextFieldView!
    @IBOutlet weak var footballExperienceTextField: HSETextView!
    @IBOutlet weak var tournamentExperienceTextField: HSETextView!
    @IBOutlet weak var contactTextField: HSETextView!
    @IBOutlet weak var aboutTextField: HSETextView!

    @IBOutlet weak var saveButton: HSEMainButton!

    // MARK: Public Properties

    var output: EditProfilePageViewOutput?

    // MARK: Private Properties

    private var data: EditProfileModel? {
        didSet {
            setupView()
        }
    }

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        output?.viewDidLoad()
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
        setupNavigationBar()
        setupProfileImageView()
        setupTextFields()
        setupSaveButton()
    }

    private func setupNavigationBar() {
        navigationItem.title = "Редактирование профиля";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes
    }

    private func setupProfileImageView() {
        let profileImageTap = UITapGestureRecognizer(target: self, action: #selector(self.changeProfileImage(_:)))
        profileImage.addGestureRecognizer(profileImageTap)

        profileImage.layer.cornerRadius = Constants.profileImageCornerRadius
        guard let photoURL = CurrentUserConfig.shared.photo else { return }
        profileImage.downloaded(from: photoURL)
    }

    private func setupTextFields() {
        setupNameTextField()
        setupFootballExperienceTextField()
        setupTournamentExperienceTextField()
        setupContactTextField()
        setupAboutTextField()
    }

    private func setupNameTextField() {
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
            maxLength: 40,
            predicteFormat: "[А-Яа-яЁёa-zA-Z ]+$"
        )
    }

    private func setupFootballExperienceTextField() {
        footballExperienceTextField.configure(
            label: "ФУТБОЛЬНЫЙ ОПЫТ",
            hintText: "Расскажите про свой опыт",
            text: CurrentUserConfig.shared.footballExperience,
            maxNamberOfCharacters: 200
        )
    }

    private func setupTournamentExperienceTextField() {
        tournamentExperienceTextField.configure(
            label: "ОПЫТ В ТУРНИРАХ НИУ ВШЭ",
            hintText: "Расскажите в каких турнирах НИУ ВШЭ вы играли",
            text: CurrentUserConfig.shared.tournamentExperience,
            maxNamberOfCharacters: 200
        )
    }

    private func setupContactTextField() {
        contactTextField.configure(
            label: "КОНТАКТНАЯ ИНФОРМАЦИЯ",
            hintText: "Телефон, Telegram...",
            text: CurrentUserConfig.shared.contact,
            maxNamberOfCharacters: 40
        )
    }

    private func setupAboutTextField() {
        aboutTextField.configure(
            label: "О СЕБЕ",
            hintText: "Расскажите о своём опыте и достижениях",
            text: CurrentUserConfig.shared.about,
            maxNamberOfCharacters: 200
        )
    }

    private func setupSaveButton() {
        saveButton.addTarget(self, action: #selector(save), for: .touchUpInside)
    }

    // MARK: Actions

    @objc private func changeProfileImage(_ sender: UITapGestureRecognizer? = nil) {
        ImagePickerControllerService.shared.showActionSheet(vc: self)
        ImagePickerControllerService.shared.imagePickedBlock = { [weak self] image in
            guard let self else { return }
            self.profileImage.image = image
            self.output?.profilePhotoChanged()
        }
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
        output?.viewDidStartEditiong()
    }

    @objc private func keyboardWillHide(notification: NSNotification) {
        output?.viewDidEndEditing()
    }

    @objc private func save(sender: UIButton) {
        output?.save(withPhoto: profileImage.image)
    }
}

// MARK: - EditProfilePageViewInput

extension EditProfilePageViewController: EditProfilePageViewInput {

    func setupData(_ data: EditProfileModel) {
        self.data = data
    }

    func setLoadingState() {
        saveButton.isLoading = true
    }

    func removeLoadingState() {
        saveButton.isLoading = false
    }

    func getData() -> EditProfileModel {
        return EditProfileModel(
            photo: CurrentUserConfig.shared.photo,
            name: nameTextField.text,
            footballExperience: footballExperienceTextField.isEmpty() ? nil : footballExperienceTextField.text,
            tournamentExperience: tournamentExperienceTextField.isEmpty() ? nil : tournamentExperienceTextField.text,
            contact: contactTextField.isEmpty() ? nil : contactTextField.text,
            about: aboutTextField.isEmpty() ? nil : aboutTextField.text
        )
    }

    func validate() -> Bool {
        nameTextField.validate()
    }

    func showAlert() {
        let alert = UIAlertController(title: "Ошибка сети", message: "Проверьте подключение и повторите попытку", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }

    func setNameNormalState() {
        nameTextField.validationState = .normal
    }

    func setNameErrorState() {
        nameTextField.validationState = .error
    }
}
