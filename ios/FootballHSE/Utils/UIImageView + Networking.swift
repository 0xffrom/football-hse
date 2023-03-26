//
//  UIImageView + Networking.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 18.03.2023.
//

import UIKit

extension UIImageView {

    func downloaded(from url: URL, contentMode mode: ContentMode = .scaleAspectFill) {
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard
                let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                let data = data, error == nil,
                let image = UIImage(data: data)
                else { return }
            DispatchQueue.main.async() { [weak self] in
                guard let self = self else { return }
                self.image = image
                self.contentMode = mode
            }
        }.resume()
    }

    func downloaded(from link: String, contentMode mode: ContentMode = .scaleAspectFill) {
        DispatchQueue.global().async {
            guard let url = URL(string: link) else { return }
            self.downloaded(from: url, contentMode: mode)
        }
    }
}
