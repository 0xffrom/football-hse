//
//  ImagePickerControllerService.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 30.04.2023.
//

import UIKit

final class ImagePickerControllerService: NSObject {

    static let shared = ImagePickerControllerService()

    fileprivate weak var currentVC: UIViewController?

    //MARK: Internal Properties

    var imagePickedBlock: ((UIImage) -> Void)?

    func showActionSheet(vc: UIViewController) {
            currentVC = vc
            let actionSheet = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)

            actionSheet.addAction(UIAlertAction(title: "Камера", style: .default, handler: { (alert:UIAlertAction!) -> Void in
                self.camera()
            }))

            actionSheet.addAction(UIAlertAction(title: "Галерея", style: .default, handler: { (alert:UIAlertAction!) -> Void in
                self.photoLibrary()
            }))

            actionSheet.addAction(UIAlertAction(title: "Отмена", style: .cancel, handler: nil))

            vc.present(actionSheet, animated: true, completion: nil)
        }

    private func camera() {
        if UIImagePickerController.isSourceTypeAvailable(.camera){
            let myPickerController = UIImagePickerController()
            myPickerController.delegate = self;
            myPickerController.sourceType = .camera

            myPickerController.allowsEditing = true

            currentVC?.present(myPickerController, animated: true, completion: nil)
        } else {
            let alert = UIAlertController(title: "Нет доступа к камере", message: nil, preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            currentVC?.present(alert, animated: true, completion: nil)
        }
    }

    private func photoLibrary() {
        if UIImagePickerController.isSourceTypeAvailable(.photoLibrary){
            let myPickerController = UIImagePickerController()
            myPickerController.delegate = self;
            myPickerController.sourceType = .photoLibrary

            myPickerController.allowsEditing = true

            currentVC?.present(myPickerController, animated: true, completion: nil)
        } else {
            let alert = UIAlertController(title: "Нет доступа к галерее", message: nil, preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            currentVC?.present(alert, animated: true, completion: nil)
        }
    }
}

extension ImagePickerControllerService: UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        currentVC?.dismiss(animated: true, completion: nil)
    }

    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        guard let selectedImage = info[.editedImage] as? UIImage else {
            print("Something went wrong")
            return
        }
        self.imagePickedBlock?(selectedImage)
        currentVC?.dismiss(animated: true, completion: nil)
    }
}
