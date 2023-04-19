using HSE_Football_Backend.Libs;
using HSE_Football_Backend.Models;
using Microsoft.AspNetCore.SignalR;

namespace HSE_Football_Backend.Other
{
    /// <summary>
    /// Класс с информацией о подключениях к чату
    /// </summary>
    public class UserChatInfo
    {
        /// <summary>
        /// ID подключения
        /// </summary>
        public string ConnectionID { get; set; }

        /// <summary>
        /// Номер телефона
        /// </summary>
        public string Phone { get; set; }

        /// <summary>
        /// Конструктор
        /// </summary>
        /// <param name="phone">Номер телефона</param>
        /// <param name="connectionId">ID подключения</param>
        public UserChatInfo(string phone, string connectionId)
        {
            this.Phone = phone;
            this.ConnectionID = connectionId;
        }
    }

    /// <summary>
    /// Хаб всех чатов
    /// </summary>
    public class ChatHub : Hub
    {
        public static List<UserChatInfo> users = new List<UserChatInfo>();

        /// <summary>
        /// Отправка сообщения
        /// </summary>
        /// <param name="phone">Номер телефона получателя</param>
        /// <param name="message">Сообщение</param>
        public async Task Send(string phone, Message message)
        {
            // Находим пользователя по почте в подключенных к хабу
            var user = users.Where(u => u.Phone == phone).FirstOrDefault();
            if (user != null)
            {
                await Clients.Client(user.ConnectionID).SendAsync("Receive", message);
                await Clients.Caller.SendAsync("Receive", message);
            }
            else
            {
                //// Отправка смс уведомления
                //if (phone != "88888888888")
                //{
                //    SmsAero smsc = new SmsAero("ckd001@mail.ru", "foR-O2fmlGrCjzzD-dF-x3cBLIbNmqAF");
                //    await smsc.SmsSend($"У вас новое сообщение в футболе ВШЭ.", phone);
                //}
                await Clients.Caller.SendAsync("Receive", message);
            }
        }

        /// <summary>
        /// Сопоставление номера телефона с ID подключения
        /// </summary>
        /// <param name="phone">Номер телефона</param>
        public void SetPhone(string phone)
        {
            // Находим пользователя с текущим ID подключения в подключенных к хабу
            var user = users.SingleOrDefault(u => u.ConnectionID == Context.ConnectionId);
            // Если такой есть, то устанавливаем номер телефона
            if (user != null)
                user.Phone = phone;
        }

        /// <summary>
        /// Подключение клиента к хабу
        /// </summary>
        public override Task OnConnectedAsync()
        {
            // Находим пользователя с текущим ID подключения в подключенных к хабу
            var user = users.Where(u => u.ConnectionID == Context.ConnectionId).SingleOrDefault();
            // Если такого нет, то добавляем с пустым значением номера телефона
            if (user == null)
            {
                user = new UserChatInfo("", Context.ConnectionId);
                users.Add(user);
            }
            return base.OnConnectedAsync();
        }

        /// <summary>
        /// Отключение клиента от хаба
        /// </summary>
        public override Task OnDisconnectedAsync(Exception e)
        {
            // Находим пользователя с текущим ID подключения в подключенных к хабу
            var user = users.Where(p => p.ConnectionID == Context.ConnectionId).FirstOrDefault();
            // Если такой есть, то удаляем его из списка подключенных
            if (user != null)
                users.Remove(user);
            return base.OnDisconnectedAsync(e);
        }
    }
}
