//
//  EditProfilePagePresenter.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

final class EditProfilePagePresenter {

    // MARK: Public Properties

    weak var view: EditProfilePageViewInput?
    var moduleOutput: EditProfilePageModuleOutput?
    
    
    // MARK: Private Properties

    private let interactor: EditProfilePageInteractorInput

    // MARK: Lifecycle

    init(interactor: EditProfilePageInteractorInput) {
        self.interactor = interactor
    }

    // MARK: Private
}

// MARK: - EditProfilePageModuleInput

extension EditProfilePagePresenter: EditProfilePageModuleInput {}

// MARK: - EditProfilePageInteractorOutput

extension EditProfilePagePresenter: EditProfilePageInteractorOutput {}

// MARK: - EditProfilePageViewOutput

extension EditProfilePagePresenter: EditProfilePageViewOutput {

    func save() {
        moduleOutput?.back()
    }
}
