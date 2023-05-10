//
//  NetworkService + Chat.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 09.05.2023.
//

import Foundation
import SwiftSignalRClient

extension NetworkService: HubConnectionDelegate {

    func connectionDidOpen(hubConnection: HubConnection) {
        guard let phoneNumber = CurrentUserConfig.shared.phoneNumber else { return }

        connection.invoke(method: "SetPhone", phoneNumber) { error in
            if let error = error {
                print("error: \(error)")
            } else {
                print(#function)
            }
        }
    }

    func connectionDidFailToOpen(error: Error) {
        print(#function)
        print(error)
        // startMessaging()
    }

    func connectionDidClose(error: Error?) {
        if error != nil {
           // Api.shared.startMessaging()
        }
        print(#function)
        print(error as Any)
    }

    private func initSignalRService() {
        let url = URL(string: "http://hse-football.ru/chat/")!
        connection = HubConnectionBuilder(url: url).withAutoReconnect().withLogging(minLogLevel: .error).build()
        connection.delegate = self
        connection.on(method: "Receive", callback: { (message: MessageModel) in
            print(#function)
            print(message.text as Any)
            self.handleMessage?(message)
        })
        connection.on(method: "Send", callback: { () in
            print(#function)
        })
        connection.start()
    }

    func startMessaging() {
        initSignalRService()
    }

    func sendMessage(phoneNumber: String, message: MessageModel, complitionSeccess: ((MessageModel) -> Void)?, complitionFail: (() -> Void)?) {
        connection.invoke(method: "Send", arguments: [phoneNumber, message]) { error in
            if  error != nil {
                self.startMessaging()
                complitionFail?()
            } else {
               complitionSeccess?(message)
            }
        }
    }

    func stopMessaging() {
        connection.stop()
    }

    func setHandleMessageAction(_ action: ((MessageModel) -> Void)?) {
        handleMessage = action
    }
}
