//
//  ConversationViewController.swift
//  FootballHSE
//
//  Created by Екатерина on 11.01.2023.
//

import UIKit
import SnapKit

final class ConversationViewController: UIViewController {

    // MARK: Outlets

    private let tableView = UITableView()
    private lazy var entryMessageView: EntryMessageView = {
        let view = R.nib.entryMessageView(withOwner: self)!
        return view
    }()
    private let refreshControl = UIRefreshControl()

    // MARK: Public Properties

    var output: ConversationViewOutput?
    var navigationTitle: String?
    var image: UIImage?

    // MARK: Private Properties

    var data: [MessageModel] = [] {
        didSet {
            tableView.reloadData()
        }
    }
    private var hightOfKeyboard: CGFloat?

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()

        hideKeyboardWhenTappedAround()
        registerKeyboardNotifications()
        configureTapGestureRecognizer()
        setupView()
        scrollToBottom(animated: false)
        scrollToBottom(animated: false)
        scrollToBottom(animated: false)
        output?.viewDidLoad()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.tabBarController?.tabBar.isHidden = true
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.tabBarController?.tabBar.isHidden = false
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    override var inputAccessoryView: UIView? {
        entryMessageView.snp.makeConstraints { make in
            make.height.equalTo(100)
        }
        return entryMessageView
    }

    override var canBecomeFirstResponder: Bool {
        return true
    }

    override var canResignFirstResponder: Bool {
        return true
    }

    // MARK: Private

    private func setupView() {
        setupTableView()
        setupNavigationBar()
        setupEntryMessageView()
    }

    private func setupTableView() {
        view.addSubview(tableView)
        tableView.backgroundColor = R.color.baseSurface1()
        tableView.snp.makeConstraints { make in
            make.top.bottom.leading.trailing.equalToSuperview()
        }

        tableView.register(MessageCell.self, forCellReuseIdentifier: MessageCell.identifier)

        tableView.dataSource = self
        tableView.delegate = self

        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        tableView.addSubview(refreshControl)

        tableView.translatesAutoresizingMaskIntoConstraints = false
        tableView.separatorStyle = .none
        tableView.rowHeight = UITableView.automaticDimension
        tableView.contentInset = UIEdgeInsets(top: 5, left: 0, bottom: 61, right: 0)
    }

    private func setupNavigationBar() {
        navigationItem.title = self.navigationTitle
        configureNavigationButton()
    }

    private func setupEntryMessageView() {
        entryMessageView.setSendMessageAction { [weak self] message in
            self?.output?.sendMessage(message)
        }
    }

    private func configureNavigationButton() {
        let button = UIButton(frame: CGRect(x: 0, y: 0, width: 40,
                                                   height: 40))
        setImageToProfileNavigationButton(button)
        button.isUserInteractionEnabled = false
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(customView: button)
    }

    private func setImageToProfileNavigationButton(_ profileButton: UIButton) {
        var image = self.image ?? R.image.userIcon()!
        image = image.resized(to: CGSize(width: 40,
                                         height: 40))
        profileButton.setImage(image, for: .normal)
        configureImage(profileButton)
    }

    private func configureImage(_ profileButton: UIButton) {
        profileButton.imageView?.layer.cornerRadius = profileButton.frame.size.width / 2
        profileButton.layer.cornerRadius = profileButton.frame.size.width / 2
        profileButton.contentHorizontalAlignment = .fill
        profileButton.contentVerticalAlignment = .fill
        profileButton.imageView?.contentMode = .scaleAspectFill
        profileButton.contentMode = .scaleAspectFill

        profileButton.imageView?.clipsToBounds = true
    }

    func registerKeyboardNotifications() {
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillShow(_:)),
                                               name: UIResponder.keyboardWillShowNotification,
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillShow(_:)),
                                               name: UIResponder.keyboardDidShowNotification,
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillHide(_:)),
                                               name: UIResponder.keyboardWillHideNotification,
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillHide(_:)),
                                               name: UIResponder.keyboardDidHideNotification,
                                               object: nil)
    }


    private func scrollToBottom(animated: Bool) {
        tableView.layoutIfNeeded()
        if isScrollingNecessary(entryMessageView) {
            let bottomOffset = entryMessageView.textView.isFirstResponder ?
            getBottomOffsetWithKeyboard() :
            getBottomOffsetWithoutKeyboard(entryMessageView)

            tableView.setContentOffset(bottomOffset, animated: animated)
        }
    }

    private func isScrollingNecessary(_ entryMessageView: EntryMessageView) -> Bool {
        let bottomOffset = entryMessageView.textView.isFirstResponder ? hightOfKeyboard : entryMessageView.bounds.size.height
        return tableView.contentSize.height > view.bounds.size.height - (bottomOffset ?? 0) - 70
    }

    private func getBottomOffsetWithKeyboard() -> CGPoint {
        tableView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: hightOfKeyboard ?? 0, right: 0)
        return CGPoint(x: 0, y: tableView.contentSize.height - view.bounds.size.height + (hightOfKeyboard ?? 0))
    }

    private func getBottomOffsetWithoutKeyboard(_ entreMessageBar: EntryMessageView?) -> CGPoint {
        tableView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 61, right: 0)
        return CGPoint(x: 0, y: tableView.contentSize.height - view.bounds.size.height + (entreMessageBar?.bounds.size.height ?? 0))
    }

    private func configureTapGestureRecognizer() {
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self,
                                                                 action: #selector(dismissKeyboardAction))
        tap.delegate = self
        view.addGestureRecognizer(tap)
    }

    // MARK: Actions

    @objc private func refresh(_ sender: AnyObject) {
        output?.viewWantToLoadMoreData()
    }

    @objc func keyboardWillShow(_ notification: NSNotification) {
        if entryMessageView.textView.isFirstResponder {
            guard let payload = KeyboardInfo(notification) else { return }
            hightOfKeyboard = payload.frameEnd?.size.height
            entryMessageView.snp.removeConstraints()
            entryMessageView.snp.makeConstraints { make in
                make.height.equalTo(75)
            }
        }
        scrollToBottom(animated: false)
    }

    @objc func keyboardWillHide(_ notification: NSNotification) {
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let self else { return }
            self.scrollToBottom(animated: true)
            self.entryMessageView.snp.removeConstraints()
            self.entryMessageView.snp.makeConstraints{ make in
                make.height.equalTo(100)
            }
            self.entryMessageView.layoutSubviews()
            self.entryMessageView.layoutIfNeeded()
            self.view.layoutSubviews()
            self.view.layoutIfNeeded()
        }
    }

    @objc func dismissKeyboardAction() {
        entryMessageView.textView.resignFirstResponder()
    }
}

// MARK: - ConversationViewInput

extension ConversationViewController: ConversationViewInput {

    func setupLoadingState() {
        if !refreshControl.isRefreshing {
            refreshControl.beginRefreshing()
        }
    }

    func setupDataState(with data: [MessageModel]) {
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }
        self.data = data
    }

    func setupErrorState() {
        setupEmptyState()
        let alert = UIAlertController(title: "Ошибка сети", message: "Проверьте подключение и повторите попытку", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }

    func displayReceivedMessage(message: MessageModel) {
        if message.receiver == CurrentUserConfig.shared.phoneNumber {
            data.append(message)
            tableView.reloadData()
            scrollToBottom(animated: false)
        }
    }

    func displaySentMessage(message: MessageModel) {
        data.append(message)
        tableView.reloadData()
        scrollToBottom(animated: false)
        entryMessageView.textView.text = ""
    }

    func clearEntryMessageView() {
        entryMessageView.textView.text = ""
    }

    func setupEmptyState() {
        if refreshControl.isRefreshing {
            refreshControl.endRefreshing()
        }
    }
}

// MARK: - UITableViewDelegate

extension ConversationViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return tableView.estimatedRowHeight
    }

    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if indexPath.row == 1 && data.count > 99 {
            output?.viewWantToLoadMoreData()
        }
    }
}

// MARK: - UITableViewDataSource

extension ConversationViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(
            withIdentifier: MessageCell.identifier,
            for: indexPath)
        guard let cell = cell as? MessageCell else {
            return cell
        }
        let message = data[indexPath.row]
        cell.configure(message)
        return cell
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        data.count
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        1
    }
}

extension ConversationViewController: UIGestureRecognizerDelegate {

    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
}
