//
//  HSETextView.swift
//  FootballHSE
//
//  Created by Ekaterina Shtanko on 30.04.2023.
//

import UIKit
import SnapKit

class HSETextView: UIView {

    var text: String? {
        get {
            textView.text
        }
        set {
            textView.text = newValue
        }
    }

    private lazy var labelView = UILabel()
    private lazy var wrapperView = UIView()
    private lazy var textView = UITextView()

    private var maxNamberOfCharacters: Int?
    private var label: String?
    private var hintText: String?

    override func awakeFromNib() {
        super.awakeFromNib()

        setupView()
    }

    func configure(label: String?, hintText: String? = nil, text: String? = nil, maxNamberOfCharacters: Int? = nil) {
        self.maxNamberOfCharacters = maxNamberOfCharacters

        self.label = label
        labelView.text = label

        self.hintText = hintText
        self.text = text

        wrapperView.layer.cornerRadius = 12

        guard let text else {
            textView.text = hintText
            textView.textColor = UIColor.lightGray
            return
        }
        textView.textColor = UIColor.black
    }

    func isEmpty() -> Bool {
        return textView.textColor == UIColor.lightGray
    }

    private func setupView() {
        addSubview(labelView)
        addSubview(wrapperView)

        labelView.snp.makeConstraints { make in
            make.left.right.top.equalToSuperview()
        }
        labelView.textColor = R.color.textAndIconsSecondary()
        labelView.font = .systemFont(ofSize: 12, weight: .medium)

        wrapperView.snp.makeConstraints { make in
            make.left.right.bottom.equalToSuperview()
            make.top.equalTo(labelView.snp.bottom).offset(4)
        }
        wrapperView.layer.cornerRadius = 12
        wrapperView.backgroundColor = R.color.baseSurface1()
        wrapperView.addSubview(textView)

        textView.snp.makeConstraints { make in
            make.left.equalTo(wrapperView.snp.left).offset(10)
            make.top.equalTo(wrapperView.snp.top).offset(6)
            make.right.equalTo(wrapperView.snp.right).offset(-10)
            make.bottom.equalTo(wrapperView.snp.bottom).inset(15)
            make.height.equalTo(48)
        }
        textView.backgroundColor = R.color.baseSurface1()
        textView.font = .systemFont(ofSize: 17)
        textView.delegate = self
        textView.isEditable = true
    }
}

extension HSETextView: UITextViewDelegate {

    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        guard let maxNamberOfCharacters else {
            return true
        }
        return textView.text.count + (text.count - range.length) <= maxNamberOfCharacters
    }

    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.textColor == UIColor.lightGray {
            textView.text = nil
            textView.textColor = UIColor.black
        }

        textView.superview?.layer.borderColor = UIColor(named: "BasePrimary")?.cgColor
        textView.superview?.layer.borderWidth = 1
    }

    func textViewDidEndEditing(_ textView: UITextView) {
        if textView.text.isEmpty {
            textView.text = hintText
            textView.textColor = UIColor.lightGray
        }

        textView.superview?.layer.borderWidth = 0
        textView.superview?.layer.borderColor = nil
    }
}
