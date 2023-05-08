//
//  PlayersApplicationViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class PlayersApplicationViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var iamgeView: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!

    @IBOutlet weak var rolesCollection: UICollectionView!
    @IBOutlet weak var tournamentsLabel: UILabel!
    @IBOutlet weak var footballExperienceLabel: UILabel!
    @IBOutlet weak var tournamentExperienceLabel: UILabel!
    @IBOutlet weak var contactLabel: UILabel!

    @IBOutlet weak var rolesView: UIView!
    @IBOutlet weak var tournamentsView: UIView!
    @IBOutlet weak var footballExperienceView: UIView!
    @IBOutlet weak var tournamentExperienceView: UIView!
    @IBOutlet weak var contactView: UIView!

    @IBOutlet weak var chatButton: HSEMainButton!

    // MARK: Public Properties

    var output: PlayersApplicationViewOutput?

    var data: PlayersApplicationDisplayModel? = nil

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        configureNavigationBar()
        configureImageView()
        configureRolesCollection()
        configureWrapperViews()
        configureData()
        configureChatButton()
    }

    private func configureNavigationBar() {
        navigationItem.title = "Заявка";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes
    }

    private func configureImageView() {
        iamgeView.layer.cornerRadius = iamgeView.layer.bounds.width / 2
        iamgeView.clipsToBounds = true
        iamgeView.layer.borderColor = UIColor(named: "TextIconsTertiary")?.cgColor
        iamgeView.layer.borderWidth = 1
        iamgeView.contentMode = .scaleAspectFit
    }

    private func configureRolesCollection() {
        rolesCollection.register(
            UINib(nibName: String(describing: BubbleCell.self), bundle: nil),
            forCellWithReuseIdentifier: BubbleCell.identifier
        )
        rolesCollection.dataSource = self
        rolesCollection.delegate = self
        rolesCollection.showsHorizontalScrollIndicator = false
    }

    private func configureWrapperViews() {
        rolesView.layer.cornerRadius = 12
        tournamentsView.layer.cornerRadius = 12
        footballExperienceView.layer.cornerRadius = 12
        tournamentExperienceView.layer.cornerRadius = 12
        contactView.layer.cornerRadius = 12
    }

    private func configureData() {
        nameLabel.text = data?.name
        iamgeView.image = data?.photo ?? R.image.userIcon()!
        tournamentsLabel.text = data?.tournaments
        footballExperienceLabel.text = data?.footballExperience ?? "Не указан"
        tournamentExperienceLabel.text = data?.tournamentExperience ?? "Не указан"
        contactLabel.text = data?.contact ?? "Не указана"
    }

    private func configureChatButton() {
        if data?.playerPhoneNumber == CurrentUserConfig.shared.phoneNumber {
            chatButton.isHidden = true
        } else {
            chatButton.isHidden = false
        }
    }
}


extension PlayersApplicationViewController: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        rolesCollection.deselectItem(at: indexPath, animated: true)
    }
}

extension PlayersApplicationViewController: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        data!.footballPosition.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = rolesCollection.dequeueReusableCell(
            withReuseIdentifier: BubbleCell.identifier,
            for: indexPath)
        let role = data!.footballPosition[indexPath.row]
        guard let searchCell = cell as? BubbleCell else {
            return cell
        }
        searchCell.configure(content: role.getStringValue())
        return searchCell
    }
}

// MARK: - PlayersApplicationViewInput

extension PlayersApplicationViewController: PlayersApplicationViewInput {

}
