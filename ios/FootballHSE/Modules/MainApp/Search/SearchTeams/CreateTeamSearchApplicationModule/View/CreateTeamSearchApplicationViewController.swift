//
//  CreateTeamSearchApplicationViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class CreateTeamSearchApplicationViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var roleTextField: HSESmartTextFieldView!
    @IBOutlet weak var preferencesTextField: HSESmartTextFieldView!

    @IBOutlet weak var saveButton: HSEMainButton!

    // MARK: Public Properties

    var output: CreateTeamSearchApplicationViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        hideKeyboardWhenTappedAround()
        setupView()
    }

    // MARK: Private

    private func setupView() {
        setupTextFields()
        navigationItem.title = "Создание заявки";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes

        saveButton.addTarget(self, action: #selector(saveApplication), for: .touchUpInside)
    }

    private func setupTextFields() {
        roleTextField.cornerRadius = 12
        roleTextField.horizontalInset = 16
        roleTextField.verticalInset = 12
        roleTextField.hightOfTextField = 48
        roleTextField.descriptionLabelText = "АМПЛУА"
        roleTextField.placeholder = "Введите позиции"

        preferencesTextField.cornerRadius = 12
        preferencesTextField.horizontalInset = 16
        preferencesTextField.verticalInset = 12
        preferencesTextField.hightOfTextField = 48
        preferencesTextField.descriptionLabelText = "ПРЕДПОЧТИЕЛЬНЫЕ ТУРНИРЫ"
        preferencesTextField.placeholder = "Перечислите турниры"
    }

    // MARK: Actions

    @objc func saveApplication(sender: UIButton) {
        output?.save()
    }
}

// MARK: - CreateTeamSearchApplicationViewInput

extension CreateTeamSearchApplicationViewController: CreateTeamSearchApplicationViewInput {

}
