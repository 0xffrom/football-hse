//
//  MessageCell.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 09.05.2023.
//

import UIKit

class MessageCell: UITableViewCell {

    private enum Const {
        static let messageLabelBoarderConstraint: CGFloat = 16
        static let messageLabelLeadingAndTrailingConstraint: CGFloat = 16 * 2
        static let messageLabelWidth: CGFloat = UIScreen.main.bounds.size.width * 3/4 - Const.messageLabelBoarderConstraint * 3
        static let cornerRadius: CGFloat = 12
        static let topConstant: CGFloat = 16
        static let bottomConstant: CGFloat = -32
    }

    static let identifier = String(describing: MessageCell.self)

    private let messageLabel = UILabel()
    private let bubbleBackgroundView = UIView()
    private let timeLabel = UILabel()

    private var leadingConstraint: NSLayoutConstraint!
    private var trailingConstraint: NSLayoutConstraint!

    func configure(_ message: MessageModel) {
        messageLabel.text = message.text
        timeLabel.text = message.sendTime.convertToMinutes()

        bubbleBackgroundView.layer.borderColor = R.color.textIconsTertiary()?.cgColor
        bubbleBackgroundView.layer.borderWidth = 0.2

        let isIncoming = message.receiver == CurrentUserConfig.shared.phoneNumber
        bubbleBackgroundView.backgroundColor = isIncoming ?
        R.color.roleCellBackground() : R.color.baseSurface2()
        messageLabel.textColor = .black

        if isIncoming {
            trailingConstraint.isActive = false
            leadingConstraint.isActive = true
        } else {
            leadingConstraint.isActive = false
            trailingConstraint.isActive = true
        }
    }

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)

        backgroundColor = R.color.baseSurface1()

        addSubview(bubbleBackgroundView)
        addSubview(messageLabel)
        addSubview(timeLabel)

        messageLabel.numberOfLines = 0
        self.isUserInteractionEnabled = false
        bubbleBackgroundView.layer.cornerRadius = Const.cornerRadius
        bubbleBackgroundView.translatesAutoresizingMaskIntoConstraints = false
        messageLabel.translatesAutoresizingMaskIntoConstraints = false
        timeLabel.translatesAutoresizingMaskIntoConstraints = false
        timeLabel.textColor = R.color.textIconsTertiary()!
        timeLabel.font = .systemFont(ofSize: 8)
        configureConstraints()
    }

    private func configureConstraints() {
        NSLayoutConstraint.activate([
            messageLabel.topAnchor.constraint(equalTo: topAnchor, constant: Const.topConstant),
            messageLabel.bottomAnchor.constraint(equalTo: bottomAnchor, constant: Const.bottomConstant),
            messageLabel.widthAnchor.constraint(lessThanOrEqualToConstant: Const.messageLabelWidth),

            bubbleBackgroundView.topAnchor.constraint(equalTo: messageLabel.topAnchor, constant: -Const.messageLabelBoarderConstraint),
            bubbleBackgroundView.leadingAnchor.constraint(equalTo: messageLabel.leadingAnchor, constant: -Const.messageLabelBoarderConstraint),
            bubbleBackgroundView.bottomAnchor.constraint(equalTo: messageLabel.bottomAnchor, constant: Const.messageLabelBoarderConstraint),
            bubbleBackgroundView.trailingAnchor.constraint(equalTo: messageLabel.trailingAnchor, constant: Const.messageLabelBoarderConstraint),
        ])

        timeLabel.snp.makeConstraints { make in
            make.bottom.equalTo(bubbleBackgroundView.snp.bottom).inset(3)
            make.trailing.equalTo(bubbleBackgroundView.snp.trailing).inset(6)
        }

        leadingConstraint = messageLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: Const.messageLabelLeadingAndTrailingConstraint)
        trailingConstraint = messageLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -Const.messageLabelLeadingAndTrailingConstraint)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
