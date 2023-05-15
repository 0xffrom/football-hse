//
//  SupportCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 08.05.2023.
//

import UIKit

class SupportCell: UITableViewCell {

    static let identifier = String(describing: SupportCell.self)

    @IBOutlet weak var lastMessageLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var unreadSignImageView: UIImageView!
    @IBOutlet weak var checkSignImageView: UIImageView!

    override func prepareForReuse() {
        super.prepareForReuse()
        checkSignImageView.isHidden = false
    }

    func configure(data: ConversationDisplayModel) {
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

        timeLabel.snp.updateConstraints { make in
            make.width.equalTo(timeLabel.intrinsicContentSize.width)
        }
    }
}
