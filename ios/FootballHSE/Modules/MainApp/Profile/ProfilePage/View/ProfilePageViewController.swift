//
//  ProfilePageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class ProfilePageViewController: UIViewController {
    
    // MARK: Private data structures

    // MARK: Outlets

    // MARK: Public Properties

    var output: ProfilePageViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }

    // MARK: Private

    private func setupView() {

    }
}


// MARK: - ProfilePageViewInput

extension ProfilePageViewController: ProfilePageViewInput {

}
