//
//  RegisterTeamPageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class RegisterTeamPageViewController: UIViewController {

    // MARK: Private data structures

    private enum Constants {
        static let initialImage = R.image.addPhotoImage()
        static let teamImageCornerRadius: CGFloat = 16
    }

    // MARK: Outlets

    @IBOutlet weak var teamImage: UIImageView!

    @IBOutlet weak var nameTextField: HSESmartTextFieldView!
    @IBOutlet weak var infoTextField: HSETextView!

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
        setupTeamImageView()
        setupTextFields()
        navigationItem.title = "Регистрация команды";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes
        saveButton.addTarget(self, action: #selector(save), for: .touchUpInside)
    }

    private func setupTeamImageView() {
        teamImage.image = Constants.initialImage

        let imageTap = UITapGestureRecognizer(target: self, action: #selector(self.changeTeamImage(_:)))
        teamImage.addGestureRecognizer(imageTap)

        teamImage.layer.cornerRadius = Constants.teamImageCornerRadius
    }

    private func setupTextFields() {
        nameTextField.cornerRadius = 12
        nameTextField.horizontalInset = 16
        nameTextField.verticalInset = 12
        nameTextField.hightOfTextField = 48
        nameTextField.autocorrectionType = .no
        nameTextField.descriptionLabelText = "НАЗВАНИЕ КОМАНДЫ"
        nameTextField.placeholder = "Введите название"
        nameTextField.errorMessage = "Некорректное название"
        nameTextField.configureRestrictions(
            minLength: 2,
            maxLength: 40,
            predicteFormat: "[А-Яа-яЁёa-zA-Z]+$"
        )

        infoTextField.configure(
            label: "ИНФОРМАЦИЯ О КОМАНДЕ",
            hintText: "Расскажите о вашей команде",
            maxNamberOfCharacters: 200
        )
    }


    // MARK: Actions

    @objc private func changeTeamImage(_ sender: UITapGestureRecognizer? = nil) {
        ImagePickerControllerService.shared.showActionSheet(vc: self)
        ImagePickerControllerService.shared.imagePickedBlock = { [weak self] image in
            guard let self else { return }
            self.teamImage.image = image
            self.output?.teamPhotoChanged()
        }
    }

    @objc private func save(sender: UIButton) {
        output?.save(withPhoto: teamImage.image)
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
        output?.viewDidStartEditiong()
    }

    @objc private func keyboardWillHide(notification: NSNotification) {
        output?.viewDidEndEditing()
    }
}

// MARK: - RegisterTeamPageViewInput

extension RegisterTeamPageViewController: RegisterTeamPageViewInput {

    func setLoadingState() {
        saveButton.isLoading = true
    }

    func removeLoadingState() {
        saveButton.isLoading = false
    }

    func getData() -> CreateTeamModel {
        return CreateTeamModel(
            photo: nil,
            name: nameTextField.text,
            about: infoTextField.isEmpty() ? nil : infoTextField.text
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
