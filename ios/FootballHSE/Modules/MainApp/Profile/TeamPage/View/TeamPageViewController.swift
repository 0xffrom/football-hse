//
//  TeamPageViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class TeamPageViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var teamImageView: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!

    @IBOutlet weak var statusView: UIView!
    @IBOutlet weak var statusLabel: UILabel!

    @IBOutlet weak var aboutView: UIView!
    @IBOutlet weak var aboutLabel: UILabel!

    @IBOutlet weak var stackView: UIStackView!

    @IBOutlet weak var deleteButton: HSEMainButton!

    private lazy var teamApplications: TeamsApplicationsView! = {
        let view = R.nib.teamsApplicationsView(withOwner: self)!
        return view
    }()

    // MARK: Public Properties

    var output: TeamPageViewOutput?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        configureNavigationBar()
        configureTeamImage()
        configureNameLabel()
        configureStatusView()
        configureAboutView()
        configureApplicationsView()
        configureDaleteButton()
    }

    private func configureNavigationBar() {
        navigationItem.title = "Моя команда";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes
    }

    private func configureTeamImage() {
        teamImageView.layer.cornerRadius = teamImageView.layer.bounds.width / 2
        teamImageView.clipsToBounds = true
        teamImageView.layer.borderColor = UIColor(named: "TextIconsTertiary")?.cgColor
        teamImageView.layer.borderWidth = 1
        teamImageView.contentMode = .scaleAspectFit

        if let photo = CurrentTeamConfig.shared.photo {
            teamImageView.image = photo
        } else if let photoURL = CurrentTeamConfig.shared.photoUrl {
            teamImageView.downloaded(from: photoURL)
        } else {
            teamImageView.image = R.image.teamIcon()!
        }
    }

    private func configureNameLabel() {
        nameLabel.text = CurrentTeamConfig.shared.name
    }

    private func configureAboutView() {
        aboutView.layer.cornerRadius = 12
        aboutLabel.text = CurrentTeamConfig.shared.about ?? "Дополнительная информация не указана"
    }

    private func configureStatusView() {
        statusView.layer.cornerRadius = 4
        if CurrentTeamConfig.shared.status == 0 {
            statusView.backgroundColor = R.color.paleOrangeBackground()!
            statusLabel.textColor = R.color.accentOrange()!
            statusLabel.text = "Обрабатывается"
        } else {
            statusView.backgroundColor = R.color.paleGreenBackground()!
            statusLabel.textColor = R.color.accentGreen()!
            statusLabel.text = "Подтверждена"
        }
    }

    private func configureApplicationsView() {
        if CurrentTeamConfig.shared.status == 1 {
            stackView.addArrangedSubview(teamApplications)
            teamApplications.configureAction { [weak self] in
                self?.output?.openTeamApplications()
            }
        }
    }

    private func configureDaleteButton() {
        deleteButton.addTarget(self, action: #selector(deleteTeam), for: .touchUpInside)
    }

    // MARK: Actions

    @objc private func deleteTeam(sender: UIButton) {
        let alert = UIAlertController(title: "Уверены, что хотите удалить команду?", message: nil, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "Удалить", style: .default) { [weak self] _ in
            guard let self else { return }
            self.output?.deleteTeam()
        })
        alert.addAction(UIAlertAction(title: "Отмена", style: .default))
        self.present(alert, animated: true, completion: nil)
    }
}

// MARK: - TeamPageViewInput

extension TeamPageViewController: TeamPageViewInput {

    func setLoadingState() {
        deleteButton.isLoading = true
    }

    func removeLoadingState() {
        deleteButton.isLoading = false
    }

    func showAlert() {
        let alert = UIAlertController(title: "Ошибка сети", message: "Проверьте подключение и повторите попытку", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}
