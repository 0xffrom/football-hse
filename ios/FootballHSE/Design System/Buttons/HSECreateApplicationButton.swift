//
//  HSECreateApplicationButton.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 14.03.2023.
//

import UIKit

final class HSECreateApplicationButton: UIButton {

    // MARK: Private Properties

    private let label = UILabel()
    private let image = UIImageView(image: UIImage(named: "filePlusImage"))

    // MARK: Lifecycle

    override func awakeFromNib() {
        super.awakeFromNib()

        setupView()
    }

    // MARK: Overrides

    override func setTitle(_ title: String?, for state: UIControl.State) {
        label.text = title
    }

    override var isHighlighted: Bool {
        didSet {
            if isHighlighted {
                label.textColor = UIColor(named: "TextAndIconsSecondary")
            } else {
                label.textColor = UIColor(named: "TextAndIconsPrimary")
            }
        }
    }

    // MARK: Private

    private func setupView() {
        addSubview(label)
        addSubview(image)

        image.snp.makeConstraints { make in
            make.bottom.leading.top.equalToSuperview()
            make.width.equalTo(65)
        }

        label.numberOfLines = 2
        label.font = .systemFont(ofSize: 14, weight: .semibold)
        label.textColor = UIColor(named: "TextAndIconsPrimary")
        label.snp.makeConstraints { make in
            make.leading.equalTo(image.snp.trailing).offset(3)
            make.centerY.equalToSuperview()
            make.trailing.equalToSuperview().offset(-16)
        }

        backgroundColor = UIColor(named: "BaseSurface1")

        layer.cornerRadius = 12
        layer.borderColor = UIColor(named: "BasePrimary")?.cgColor
        layer.borderWidth = 1
    }
}
