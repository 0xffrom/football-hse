//
//  CreateTeamSearchApplicationViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class CreateTeamSearchApplicationViewController: UIViewController {
    
    // MARK: Private data structures

    private enum Constants {
        static let textFieldCornerRadius: CGFloat = 12
        static let noApplicationsString = "Заявок пока нет"
        static let noResultsString = "По вашему запросу ничего не нашлось"
    }
    
    // MARK: Outlets


    // MARK: Public Properties

    var output: CreateTeamSearchApplicationViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        hideKeyboardWhenTappedAround()
        setupView()
        //output?.viewDidLoad()
    }

    // MARK: Private

    private func setupView() {
        setupCreateApplicationButton()
    }

    private func setupCreateApplicationButton() {
        //createApplicationButton.setTitle("Создать заявку на поиск команды", for: .normal)
        //createApplicationButton.addTarget(self, action: #selector(createApplication), for: .touchUpInside)
    }

    // MARK: Actions

    @objc private func createApplication(sender: UIButton) {
       // output?.viewWantsToOpenCreateApplictaionScreen()
    }
}

// MARK: - CreateTeamSearchApplicationViewInput

extension CreateTeamSearchApplicationViewController: CreateTeamSearchApplicationViewInput {

}
