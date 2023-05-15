//
//  RequestSender.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 17.03.2023.
//

import UIKit
import SwiftSignalRClient

public protocol INetworkService {
    func sendGetRequest<Parser>(config: RequestConfigWithParser<Parser>, competionHandler: @escaping (Result<Parser.Model, Error>) -> Void)
    func sendPostRequest(config: RequestConfig,
                      competionHandler: ((Result<Data?, Error>) -> Void)?)
    func sendPostRequestWithParser<Parser>(config: RequestConfigWithParser<Parser>,
                                           competionHandler: @escaping (Result<Parser.Model, Error>) -> Void)
    func sendDeleteRequest(config: RequestConfig,
                      competionHandler: @escaping (Result<Data?, Error>) -> Void)
    func downlandImage(url: String?) async -> UIImage?
    func uploadImage(image: UIImage?, urlRequest: URLRequest?, competionHandler: @escaping (Result<Data?, Error>) -> Void)
    func startMessaging()
    func stopMessaging()
    func setHandleMessageAction(_ action: ((MessageModel) -> Void)?)
    func sendMessage(phoneNumber: String, message: MessageModel, completion: @escaping (Result<MessageModel, Error>) -> Void)
}

class NetworkService: INetworkService {

    let session: URLSession
    var connection: HubConnection!
    var handleMessage: ((MessageModel) -> Void)?


    init() {
        let sessionConfig = URLSessionConfiguration.default
        sessionConfig.timeoutIntervalForRequest = 30.0
        session = URLSession(configuration: sessionConfig)
    }

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
                   print(response.debugDescription)
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

    func sendPostRequestWithParser<Parser>(config: RequestConfigWithParser<Parser>,
                                           competionHandler: @escaping (Result<Parser.Model, Error>) -> Void) {
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
                if response.statusCode == 401 {
                    competionHandler(.failure(NetworkError.tokensError))
                    return
                } else {
                    print(response.debugDescription)
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
                      competionHandler: ((Result<Data?, Error>) -> Void)?) {
        guard var urlRequest = config.request.urlRequest else {
            competionHandler?(.failure(NetworkError.badURL))
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
                competionHandler?(.failure(error))
                return
            }

            guard let response = response as? HTTPURLResponse else {
                print(response.debugDescription)
                competionHandler?(.failure(NetworkError.parsingError))
                return
            }

            guard (200...299).contains(response.statusCode) else {
                print(response.statusCode)
                print(response.description)
                print(response.debugDescription)
                if response.statusCode == 401 {
                    competionHandler?(.failure(NetworkError.tokensError))
                    return
                } else {
                    competionHandler?(.failure(NetworkError.wrongParams))
                    return
                }
            }

            competionHandler?(.success(data))
        }
        task.resume()
    }

    func sendDeleteRequest(config: RequestConfig,
                      competionHandler: @escaping (Result<Data?, Error>) -> Void) {
        guard var urlRequest = config.request.urlRequest else {
            competionHandler(.failure(NetworkError.badURL))
            return
        }

        if let token = CurrentUserConfig.shared.token {
            urlRequest.setValue( "Bearer \(token)", forHTTPHeaderField: "Authorization")
        }
        urlRequest.httpMethod = "DELETE"
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

    func uploadImage(image: UIImage?, urlRequest: URLRequest?, competionHandler: @escaping (Result<Data?, Error>) -> Void) {

        guard var urlRequest else {
            competionHandler(.failure(NetworkError.badURL))
            return
        }

        let boundary = UUID().uuidString

        let session = URLSession.shared

        urlRequest.httpMethod = "POST"

        if let token = CurrentUserConfig.shared.token {
            urlRequest.setValue( "Bearer \(token)", forHTTPHeaderField: "Authorization")
        }

        urlRequest.setValue("multipart/form-data; boundary=\(boundary)", forHTTPHeaderField: "Content-Type")

        var data = Data()

        data.append("\r\n--\(boundary)\r\n".data(using: .utf8)!)
        let fileName = "image\(boundary).png"
        data.append("Content-Disposition: form-data; name=\"image\"; filename=\"\(fileName)\"\r\n".data(using: .utf8)!)
        data.append("Content-Type: image/png\r\n\r\n".data(using: .utf8)!)
        data.append(image?.pngData() ?? Data())

        data.append("\r\n--\(boundary)--\r\n".data(using: .utf8)!)

        session.uploadTask(with: urlRequest, from: data, completionHandler: { responseData, response, error in
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
        }).resume()
    }
}
