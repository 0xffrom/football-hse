//
//  HSETextFieldWithErrorState.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 05.03.2023.
//

import UIKit
import SnapKit
import InputMask

final class HSESmartTextFieldView: UIView {

    // MARK: Public Properties

    var firstResponder: Bool {
        get {
            return textField.isFirstResponder
        }
        set {
            if newValue {
                textField.becomeFirstResponder()
            } else {
                textField.resignFirstResponder()
            }
        }
    }

    var descriptionLabelText: String? {
        get {
            return descriptionLabel.text
        }
        set {
            setupDescriptionLabel(with: newValue)
        }
    }

    var hightOfTextField: ConstraintRelatableTarget {
        get {
            return textField.snp.height
        }
        set {
            textField.snp.updateConstraints { make in
                make.height.equalTo(newValue)
            }
        }
    }

    var font: UIFont? {
        get {
            return textField.font
        }
        set {
            textField.font = newValue
        }
    }

    var textContentType: UITextContentType {
        get {
            return textField.textContentType
        }
        set {
            textField.textContentType = newValue
        }
    }

    var autocapitalizationType: UITextAutocapitalizationType {
        get {
            return textField.autocapitalizationType
        }
        set {
            textField.autocapitalizationType = newValue
        }
    }

    var autocorrectionType: UITextAutocorrectionType {
        get {
            return textField.autocorrectionType
        }
        set {
            textField.autocorrectionType = newValue
        }
    }

    var textColor: UIColor? {
        get {
            return textField.textColor
        }
        set {
            textField.textColor = newValue
        }
    }

    var horizontalInset: CGFloat {
        get {
            return textField.horizontalInset
        }
        set {
            textField.horizontalInset = newValue
        }
    }

    var verticalInset: CGFloat {
        get {
            return textField.verticalInset
        }
        set {
            textField.verticalInset = newValue
        }
    }

    var cornerRadius: CGFloat {
        get {
            return textField.layer.cornerRadius
        }
        set {
            textField.layer.cornerRadius = newValue
        }
    }

    var placeholder: String? {
        get {
            return textField.placeholder
        }
        set {
            textField.placeholder = newValue
        }
    }

    var keyboardType: UIKeyboardType {
        get {
            return textField.keyboardType
        }
        set {
            textField.keyboardType = newValue
        }
    }

    var text: String? {
        get {
            return textField.text
        }
        set {
            textField.text = newValue
        }
    }

    var inputValueWithoutMask: String? {
        get {
            return inputValue
        }
    }

    var errorMessage: String? {
        get {
            return errorMessageLabel.text
        }
        set {
            errorMessageLabel.text = newValue
        }
    }

    var validationState: HSETextFieldValidationState = .normal {
        didSet {
            textField.validationState = validationState
            setupErrorMessageLabel(with: validationState)
        }
    }

    // MARK: Outlets

    @IBOutlet weak var listener: MaskedTextFieldDelegate!

    // MARK: Private Properties

    private lazy var textField = HSETextField()
    private lazy var descriptionLabel = UILabel()
    private lazy var errorMessageLabel = UILabel()

    private var inputValue: String?

    private var minLength: Int?
    private var maxLength: Int?
    private var predicteFormat: String?
    private var didFillMandatoryCharacters: Bool?

    private var didFillMandatoryCharactersAction: (() -> Void)?

    // MARK: Lifecycle

    override func awakeFromNib() {
        super.awakeFromNib()

        setupView()
        textField.setupView()
    }

    // MARK: Overrides

    override func becomeFirstResponder() -> Bool {
        textField.becomeFirstResponder()
    }

    override func resignFirstResponder() -> Bool {
        textField.resignFirstResponder()
    }

    // MARK: Public

    func configureRestrictions(
        minLength: Int? = nil,
        maxLength: Int? = nil,
        predicteFormat: String? = nil,
        mask: String? = nil
    ) {
        self.minLength = minLength
        self.maxLength = maxLength
        self.predicteFormat = predicteFormat

        if let mask = mask {
            textField.delegate = listener
            listener?.delegate = self
            listener?.affinityCalculationStrategy = .prefix
            listener?.affineFormats = [mask]
            didFillMandatoryCharacters = false
        }
    }

    func validate() -> Bool {
        var valid = true

        valid = valid && checkMaxLength()
        valid = valid && checkMinLength()
        valid = valid && checkPredicte()
        valid = valid && checkMask()

        if valid {
            validationState = .normal
        } else {
            validationState = .error
        }

        return valid
    }

    func setDidFillMandatoryCharactersAction(_ action: (() -> Void)?) {
        didFillMandatoryCharactersAction = action
    }

    // MARK: Private

    private func setupView() {
        addSubview(textField)
        addSubview(descriptionLabel)
        addSubview(errorMessageLabel)

        setupTextField()
        setupErrorMessageLabel()
        setupDescriptionLabel()

        validationState = .normal
    }

    private func setupTextField() {
        textField.snp.makeConstraints { make in
            make.left.right.equalToSuperview()
            make.height.equalTo(60)
        }
    }

    private func setupErrorMessageLabel() {
        errorMessageLabel.font = .systemFont(ofSize: 14)
        errorMessageLabel.textColor = UIColor(named: "AccentRed")
        errorMessageLabel.snp.makeConstraints { make in
            make.bottom.left.right.equalToSuperview()
            make.top.equalTo(textField.snp.bottom).offset(0)
            make.height.equalTo(0)
        }
    }

    private func setupDescriptionLabel() {
        descriptionLabel.font = .systemFont(ofSize: 12, weight: .medium)
        descriptionLabel.textColor = UIColor(named: "TextAndIconsSecondary")
        descriptionLabel.snp.makeConstraints { make in
            make.top.left.right.equalToSuperview()
            make.bottom.equalTo(textField.snp.top).offset(0)
            make.height.equalTo(0)
        }
    }

    private func setupErrorMessageLabel(with validationState: HSETextFieldValidationState) {
        guard errorMessage != nil else { return }

        switch validationState {
        case .normal:
            setNormalState()
        case .error:
            setErrorState()
        }
    }

    private func setupDescriptionLabel(with description: String?) {
        descriptionLabel.text = description

        if description == nil {
            makeDescriptionLabelHidden()
        } else {
            makeDescriptionLabelVisibal()
        }
    }

    private func setNormalState() {
        errorMessageLabel.isHidden = true
        errorMessageLabel.snp.updateConstraints { make in
            make.top.equalTo(textField.snp.bottom).offset(0)
            make.height.equalTo(0)
        }
    }

    private func setErrorState() {
        errorMessageLabel.snp.updateConstraints { make in
            make.top.equalTo(textField.snp.bottom).offset(4)
            make.height.equalTo(20)
        }
        errorMessageLabel.isHidden = false
    }

    private func makeDescriptionLabelHidden() {
        descriptionLabel.isHidden = true
        descriptionLabel.snp.updateConstraints { make in
            make.bottom.equalTo(textField.snp.top).offset(0)
            make.height.equalTo(0)
        }
    }

    private func makeDescriptionLabelVisibal() {
        descriptionLabel.snp.updateConstraints { make in
            make.bottom.equalTo(textField.snp.top).offset(-4)
            make.height.equalTo(17)
        }
        descriptionLabel.isHidden = false
    }

    private func checkMaxLength() -> Bool {
        guard let maxLength = maxLength else { return true }
        guard let text = textField.text else { return true }
        return text.count <= maxLength
    }

    private func checkMinLength() -> Bool {
        guard let minLength = minLength else { return true }
        guard let text = textField.text else { return false }
        return text.count >= minLength
    }

    private func checkPredicte() -> Bool {
        guard let predicteFormat = predicteFormat else { return true }
        let predicateTest = NSPredicate(format: "SELF MATCHES %@", predicteFormat)
        return predicateTest.evaluate(with: textField.text)
    }

    private func checkMask() -> Bool {
        didFillMandatoryCharacters ?? true
    }
}

extension HSESmartTextFieldView: MaskedTextFieldDelegateListener {

    func textField(
        _ textField: UITextField,
        didFillMandatoryCharacters complete: Bool,
        didExtractValue value: String
    ) {
        inputValue = value
        didFillMandatoryCharacters = complete

        if complete {
            didFillMandatoryCharactersAction?()
        }
    }
}
