//
//  HSEButton.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.03.2023.
//

import UIKit

class HSEMainButton: UIButton {

    private let spinner = UIActivityIndicatorView()

    var isLoading = false {
        didSet {
            updateView()
        }
    }

    // MARK: Lifecycle

    override func awakeFromNib() {
        super.awakeFromNib()

        setupView()
    }

    // MARK: Private

    private func setupView() {
        backgroundColor = UIColor(named: "BasePrimary")
        layer.cornerRadius = 14

        tintColor = UIColor(named: "TextIconsWhite")
        titleLabel?.font = .systemFont(ofSize: 18, weight: .semibold)
        setTitle("", for: .disabled)

        spinner.hidesWhenStopped = true
        spinner.color = .white
        spinner.style = .medium
        addSubview(spinner)
        spinner.snp.makeConstraints { make in
            make.center.equalTo(self.snp.center)
        }
    }

    private func updateView() {
        if isLoading {
            isEnabled = false
            spinner.startAnimating()
        } else {
            spinner.stopAnimating()
            isEnabled = true
        }
    }
}
