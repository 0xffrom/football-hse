//
//  RequestSender.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import Foundation

public protocol INetworkService {
    func sendGetRequest<Parser>(config: RequestConfigWithParser<Parser>, competionHandler: @escaping (Result<Parser.Model, Error>) -> Void)
    func sendPostRequest(config: RequestConfig, competionHandler: @escaping (Result<Data?, Error>) -> Void)
}

class NetworkService: INetworkService {

   let session = URLSession.shared

   func sendGetRequest<Parser>(config: RequestConfigWithParser<Parser>,
                     competionHandler: @escaping (Result<Parser.Model, Error>) -> Void) {
       guard let urlRequest = config.request.urlRequest else {
           competionHandler(.failure(NetworkError.badURL))
           return
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

        urlRequest.httpMethod = "POST"

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

            competionHandler(.success(data))
        }
        task.resume()
    }
}
