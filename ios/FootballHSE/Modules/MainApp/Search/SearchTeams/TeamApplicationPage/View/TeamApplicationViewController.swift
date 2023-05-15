//
//  TeamApplicationViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class TeamApplicationViewController: UIViewController {

    // MARK: Outlets

    @IBOutlet weak var iamge: UIImageView!
    @IBOutlet weak var name: UILabel!

    @IBOutlet weak var rolesCollection: UICollectionView!
    @IBOutlet weak var tournamentsLabel: UILabel!
    @IBOutlet weak var contactLabel: UILabel!
    @IBOutlet weak var infoLabel: UILabel!

    @IBOutlet weak var rolesView: UIView!
    @IBOutlet weak var tournamentsView: UIView!
    @IBOutlet weak var contactView: UIView!
    @IBOutlet weak var infoView: UIView!

    @IBOutlet weak var chatButton: HSEMainButton!

    // MARK: Public Properties

    var output: TeamApplicationViewOutput?

    var data: TeamApplicationDisplayModel? = nil

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        navigationItem.title = "Заявка";
        let attributes = [NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16, weight: .medium)]
        UINavigationBar.appearance().titleTextAttributes = attributes

        iamge.layer.cornerRadius = iamge.layer.bounds.width / 2
        iamge.clipsToBounds = true
        iamge.layer.borderColor = UIColor(named: "TextIconsTertiary")?.cgColor
        iamge.layer.borderWidth = 1
        iamge.contentMode = .scaleAspectFit

        rolesCollection.register(
            UINib(nibName: String(describing: BubbleCell.self), bundle: nil),
            forCellWithReuseIdentifier: BubbleCell.identifier
        )
        rolesCollection.dataSource = self
        rolesCollection.delegate = self
        rolesCollection.showsHorizontalScrollIndicator = false

        rolesView.layer.cornerRadius = 12
        tournamentsView.layer.cornerRadius = 12
        contactView.layer.cornerRadius = 12
        infoView.layer.cornerRadius = 12

        name.text = data?.name
        contactLabel.text = data?.contact ?? "Не указана"
        infoLabel.text = data?.description ?? "Не указана"
        tournamentsLabel.text = data?.tournaments
        iamge.image = data?.logo ?? R.image.teamIcon()!

        if data?.contact == CurrentUserConfig.shared.phoneNumber {
            chatButton.isHidden = true
        } else {
            chatButton.isHidden = false
        }

        chatButton.addTarget(self, action: #selector(openChat), for: .touchUpInside)
    }

    @objc func openChat() {
        output?.openChat()
    }
}


extension TeamApplicationViewController: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        rolesCollection.deselectItem(at: indexPath, animated: true)
    }
}

extension TeamApplicationViewController: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        data!.playerPosition.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = rolesCollection.dequeueReusableCell(
            withReuseIdentifier: BubbleCell.identifier,
            for: indexPath)
        let role = data!.playerPosition[indexPath.row]
        guard let searchCell = cell as? BubbleCell else {
            return cell
        }
        searchCell.configure(content: role.getStringValue())
        return searchCell
    }
}

// MARK: - TeamApplicationViewInput

extension TeamApplicationViewController: TeamApplicationViewInput {

    func setLoadingState() {
        chatButton.isLoading = true
    }

    func removeLoadingState() {
        chatButton.isLoading = false
    }

    func showAlert() {
        let alert = UIAlertController(title: "Ошибка сети", message: "Проверьте подключение и повторите попытку", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}
