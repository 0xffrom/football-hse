//
//  TeamInfoIsLoadingView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 07.05.2023.
//

import UIKit

final class TeamInfoIsLoadingView: UIView {

    @IBOutlet weak var activityIndicatorView: UIActivityIndicatorView!

    override func awakeFromNib() {
        super.awakeFromNib()

        layer.cornerRadius = 15
        clipsToBounds = true
        activityIndicatorView.startAnimating()
    }
}
