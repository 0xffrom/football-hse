//
//  CreateConversationTarget.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 10.05.2023.
//

import Foundation

struct CreateConversationTarget: RequestProtocol {
    let path = "api/Chats"

    var urlRequest: URLRequest?

    init(phoneNumber: String, name: String?, image: String?) {
        let urlString = BaseURL.stringURL + path
        guard let url = URL(string: urlString) else {
            return
        }

        urlRequest = URLRequest(url: url)

        let json: [String: Any] = ["phoneNumber1": CurrentUserConfig.shared.phoneNumber as Any,
                                   "name1": CurrentUserConfig.shared.name as Any,
                                   "photo1": CurrentUserConfig.shared.photo as Any,
                                   "phoneNumber2": phoneNumber,
                                   "name2": name as Any,
                                   "photo2": image as Any]

        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        urlRequest?.httpBody = jsonData
        urlRequest?.httpMethod = HttpMethod.POST.rawValue
    }
}
