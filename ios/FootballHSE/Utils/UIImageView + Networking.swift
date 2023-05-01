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
            else {
                return
            }

            ImagesService.shared.cachedImages[url.absoluteString] = image
            DispatchQueue.main.async() { [weak self] in
                guard let self = self else { return }
                self.image = image
                self.contentMode = mode
            }
        }.resume()
    }

    func downloaded(from link: String, contentMode mode: ContentMode = .scaleAspectFill) {
        DispatchQueue.global().async {
            if let image = ImagesService.shared.cachedImages[link] {
                DispatchQueue.main.async() { [weak self] in
                    guard let self = self else { return }
                    self.image = image
                    self.contentMode = mode
                }
            }

            guard let url = URL(string: link) else { return }
            self.downloaded(from: url, contentMode: mode)
        }
    }
}

final class ImagesService {

    static let shared = ImagesService()

    var cachedImages = [String: UIImage]()
}
