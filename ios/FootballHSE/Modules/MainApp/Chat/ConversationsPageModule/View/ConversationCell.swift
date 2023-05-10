//
//  ConversationCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 14.03.2023.
//

import UIKit

class ConversationCell: UITableViewCell {

    static let identifier = String(describing: ConversationCell.self)

    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var lastMessageLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var unreadSignImageView: UIImageView!
    @IBOutlet weak var checkSignImageView: UIImageView!

    override func awakeFromNib() {
        super.awakeFromNib()

        setupImageView()
    }

    override func prepareForReuse() {
        super.prepareForReuse()

        photoImageView.image = nil
        checkSignImageView.isHidden = false
    }

    func configure(data: ConversationDisplayModel) {
        self.nameLabel.text = data.name
        self.lastMessageLabel.text = data.lastMessage
        self.timeLabel.text = data.time
        self.unreadSignImageView.isHidden = !data.unreadMessageSign
        switch data.check {
        case .oneCheck:
            checkSignImageView.image = R.image.checkIcon()
        case .twoChecks:
            checkSignImageView.image = R.image.checkAllIcon()
        case .noChecks:
            checkSignImageView.isHidden = true
        }
        self.photoImageView.image = data.photo ?? R.image.userIcon()!

        timeLabel.snp.updateConstraints { make in
            make.width.equalTo(timeLabel.intrinsicContentSize.width)
        }
    }

    private func setupImageView() {
        photoImageView.layer.cornerRadius = photoImageView.frame.width / 2
    }
}
