//
//  HSETextField.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.03.2023.
//

import UIKit
import SnapKit

class HSETextField: UITextField {

    // MARK: Public Properties

    var horizontalInset: CGFloat = 24
    var verticalInset: CGFloat = 18

    var validationState: HSETextFieldValidationState = .normal {
        didSet {
            switch validationState {
            case .normal:
                if isFirstResponder {
                    setFocusState()
                } else {
                    setNormalState()
                }
            case .error:
                setErrorState()
            }
        }
    }

    // MARK: Lifecycle

    override func awakeFromNib() {
        super.awakeFromNib()

        setupView()
    }

    // MARK: Overrides

    override func becomeFirstResponder() -> Bool {
        let becomeFirstResponder = super.becomeFirstResponder()
        if becomeFirstResponder {
            if validationState != .error {
                setFocusState()
            }
        }
        return becomeFirstResponder
    }

    override func resignFirstResponder() -> Bool {
        let resignFirstResponder = super.resignFirstResponder()
        if resignFirstResponder {
            if validationState != .error {
                setNormalState()
            }
        }
        return resignFirstResponder
    }

    override func textRect(forBounds: CGRect) -> CGRect {
        return forBounds.insetBy(dx: horizontalInset , dy: verticalInset)
    }

    override func editingRect(forBounds: CGRect) -> CGRect {
        return forBounds.insetBy(dx: horizontalInset , dy: verticalInset)
    }

    override func placeholderRect(forBounds: CGRect) -> CGRect {
        return forBounds.insetBy(dx: horizontalInset, dy: verticalInset)
    }

    // MARK: Public

    func setupView() {
        layer.masksToBounds = true

        backgroundColor = UIColor(named: "BaseSurface1")

        borderStyle = .none

        font = .systemFont(ofSize: 17)

        textColor = UIColor(named: "TextAndIconsPrimary") ?? .black

        let attributes: [NSAttributedString.Key : Any] = [
            NSAttributedString.Key.foregroundColor: UIColor(named: "TextIconsTertiary") ?? .gray,
            .font: UIFont.systemFont(ofSize: 17)
        ]
        attributedPlaceholder = NSAttributedString(string: "", attributes: attributes)
    }

    // MARK: Private

    private func setNormalState() {
        layer.borderWidth = 0
        layer.borderColor = nil
    }

    private func setFocusState() {
        layer.borderColor = UIColor(named: "BasePrimary")?.cgColor
        layer.borderWidth = 1
    }

    private func setErrorState() {
        layer.borderColor = UIColor(named: "AccentRed")?.cgColor
        layer.borderWidth = 1
    }
}
