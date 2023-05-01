//
//  RequestSender.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import UIKit

public protocol INetworkService {
    func sendGetRequest<Parser>(config: RequestConfigWithParser<Parser>, competionHandler: @escaping (Result<Parser.Model, Error>) -> Void)
    func sendPostRequest(config: RequestConfig, competionHandler: @escaping (Result<Data?, Error>) -> Void)
    func downlandImage(url: String?) async -> UIImage?
}

class NetworkService: INetworkService {

   let session = URLSession.shared

    func downlandImage(url: String?) async -> UIImage? {
        guard let stringUrl = url, let url = URL(string: stringUrl) else {
            return nil
        }

        if let image = ImagesService.shared.cachedImages[stringUrl] {
            return image
        }

        let request = try? await URLSession.shared.data(from: url)

        guard let request, let httpURLResponse = request.1 as? HTTPURLResponse, httpURLResponse.statusCode == 200,
              let mimeType = request.1.mimeType, mimeType.hasPrefix("image"),
              let image = UIImage(data: request.0) else {
            return nil
        }

        ImagesService.shared.cachedImages[url.absoluteString] = image
        return image
    }

   func sendGetRequest<Parser>(config: RequestConfigWithParser<Parser>,
                     competionHandler: @escaping (Result<Parser.Model, Error>) -> Void) {
       guard var urlRequest = config.request.urlRequest else {
           competionHandler(.failure(NetworkError.badURL))
           return
       }

       if let token = CurrentUserConfig.shared.token {
           urlRequest.setValue( "Bearer \(token)", forHTTPHeaderField: "Authorization")
       }

       let task = session.dataTask(with: urlRequest) { (data: Data?, response: URLResponse?, error: Error?) in
           if let error = error {
               print(response.debugDescription)
               competionHandler(.failure(error))
               return
           }

           guard let response = response as? HTTPURLResponse else {
               print(response.debugDescription)
               competionHandler(.failure(NetworkError.parsingError))
               return
           }

           guard (200...299).contains(response.statusCode) else {
               if response.statusCode == 401 {
                   competionHandler(.failure(NetworkError.tokensError))
                   return
               } else {
                   competionHandler(.failure(NetworkError.wrongParams))
                   return
               }
           }

           guard let data = data,
                 let parsedModel: Parser.Model = config.parser.parse(data: data) else {
               let a = response.statusCode
               competionHandler(.failure(NetworkError.parsingError))
               return
           }
           competionHandler(.success(parsedModel))
       }
       task.resume()
   }

    func sendPostRequest(config: RequestConfig,
                      competionHandler: @escaping (Result<Data?, Error>) -> Void) {
        guard var urlRequest = config.request.urlRequest else {
            competionHandler(.failure(NetworkError.badURL))
            return
        }

        if let token = CurrentUserConfig.shared.token {
            urlRequest.setValue( "Bearer \(token)", forHTTPHeaderField: "Authorization")
        }
        urlRequest.addValue("application/json", forHTTPHeaderField: "Content-Type")
        urlRequest.addValue("text/plain", forHTTPHeaderField: "accept")

        let task = session.dataTask(with: urlRequest) { (data: Data?, response: URLResponse?, error: Error?) in
            if let error = error {
                print(response.debugDescription)
                competionHandler(.failure(error))
                return
            }

            guard let response = response as? HTTPURLResponse else {
                print(response.debugDescription)
                competionHandler(.failure(NetworkError.parsingError))
                return
            }

            guard (200...299).contains(response.statusCode) else {
                print(response.statusCode)
                print(response.description)
                print(response.debugDescription)
                if response.statusCode == 401 {
                    competionHandler(.failure(NetworkError.tokensError))
                    return
                } else {
                    competionHandler(.failure(NetworkError.wrongParams))
                    return
                }
            }

            competionHandler(.success(data))
        }
        task.resume()
    }
}
