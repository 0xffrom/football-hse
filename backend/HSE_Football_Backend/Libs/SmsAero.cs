// smsaero.ru API

using System;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace HSE_Football_Backend.Libs
{
    public class SmsAero
    {
        /// <summary>
        /// API V2 example
        /// </summary>
        public static async void ExampleUsage()
        {
            SmsAero smsc = new SmsAero("", "");

            // Отправка SMS сообщений
            string[] numbers = { "78945612309", "78945612303" };
            await smsc.SmsSend("Привет!", numbers: numbers);

            // Проверка статуса SMS сообщения
            await smsc.CheckSend(44197982);

            // Получение списка отправленных сообщений
            await smsc.SmsList(text: "Hello");

            // Запрос баланса
            await smsc.Balance();

            // Тестовый метод для проверки авторизации
            await smsc.Auth();

            // Получение списка платёжных карт
            await smsc.Cards();

            // Пополнение баланса
            await smsc.AddBalance(100, 12345);

            // Запрос тарифа
            await smsc.Tariffs();

            // Добавление подписи
            await smsc.SignAdd("sign_test");

            // Получить список подписей
            await smsc.SignList();

            // Добавление группы
            await smsc.GroupAdd("Fish");

            // Удаление группы
            await smsc.GroupDelete(231697);

            // Получение списка групп
            await smsc.GroupList();

            // Добавление контакта
            await smsc.ContactAdd("700000000000", fname: "Filip", sex: Sex.male);

            // Удаление контакта
            await smsc.ContactDelete(123);

            // Список контактов
            await smsc.ContactList("71234567890");

            // Добавление в чёрный список
            await smsc.BlackListAdd("71234567890");

            // Удаление из черного списка
            await smsc.BlackListDelete(123);

            // Список контактов в черном списке
            await smsc.BlackListGet("791234567890");

            // Создание запроса на проверку HLR
            await smsc.CheckHLR(numbers: new[] { "71234567890", "71234567891" });

            // Определение оператора
            await smsc.NumberOperator("791234567890");

            //Отправка Viber-рассылок
            await smsc.ViberSend(ViberChannel.INFO, "Тестовое сообщение", number: "70000000000", sign: "Bonus");

            //Статистика по Viber - рассылке
            await smsc.ViberStatistic(123);

            //Список Viber-рассылок
            await smsc.ViberList();

            //Список доступных подписей для Viber - рассылок
            await smsc.ViberSignList();
        }

        private readonly HttpClient _http = new HttpClient();

        // Константы с параметрами отправки
        private readonly bool Debug = false; // Вывод информации об отправке
        private readonly string _from; // 

        /// <summary>
        /// 
        /// </summary>
        /// <param name="smsAeroLogin">логин</param>
        /// <param name="smsAeroApiKey">ваш api-key</param>
        /// <param name="from">подпись отправителя</param>
        public SmsAero(string smsAeroLogin, string smsAeroApiKey, string from = "SMS Aero")
        {
            _from = UrlEncode(from);

            var basicAuthData =
                Convert.ToBase64String(Encoding.GetEncoding("ISO-8859-1")
                    .GetBytes(smsAeroLogin + ":" + smsAeroApiKey));

            // Авторизация по умолчанию для HttpClient
            _http.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Basic", basicAuthData);
        }

        /// <summary>
        /// Отправка SMS сообщения
        /// </summary>
        /// <param name="text">Текст сообщения</param>
        /// <param name="number">Номер телефона</param>
        /// <param name="numbers">Номера телефона</param>
        /// <param name="dateSend">Дата для отложенной отправки сообщения</param>
        /// <param name="callbackUrl">url для отправки статуса сообщения в формате http://mysite.com или https://mysite.com (в ответ система ждет статус 200)</param>
        /// <param name="sign">Подпись отправителя</param>
        /// <returns>string(json)</returns>
        public async Task<string> SmsSend(string text, string number = "",
            string[]? numbers = null, DateTime? dateSend = null, string callbackUrl = "", string sign = "")
        {
            var data = $"sign={(sign == "" ? _from : UrlEncode(sign))}&text={UrlEncode(text)}";

            if (number != "") data += $"&number={number}";
            if (numbers != null) data += numbers.Aggregate("", (s, s1) => $"{s}&numbers[]={s1}");
            if (dateSend != null) data += $"&dateSend={ToUnixTime((DateTime)dateSend)}";
            if (callbackUrl != "") data += $"&callbackUrl={UrlEncode(callbackUrl)}";

            return await Send(data, "sms/send");
        }

        /// <summary>
        /// Проверка статуса SMS сообщения
        /// </summary>
        /// <param name="id">Идентификатор сообщения</param>
        /// <returns>string(json)</returns>
        public async Task<string> CheckSend(int id)
            => await Send($"id={id}", "sms/status");

        /// <summary>
        /// Получение списка отправленных sms сообщений
        /// </summary>
        /// <param name="number">Фильтровать сообщения по номеру телефона</param>
        /// <param name="text">Фильтровать сообщения по тексту</param>
        /// <param name="page">Номер страницы</param>
        /// <returns>string(json)</returns>
        public async Task<string> SmsList(string number = "", string text = "", int? page = null)
        {
            var data = "";

            if (number != "") data += $"number={number}&";
            if (text != "") data += $"text={UrlEncode(text)}&";
            if (page != null) data += $"page={page}";

            return await Send(data, "sms/list");
        }

        /// <summary>
        /// Запрос баланса
        /// </summary>
        /// <returns>string(json)</returns>
        public async Task<string> Balance()
            => await Send("balance");

        /// <summary>
        /// Тестовый метод для проверки авторизации
        /// </summary>
        /// <returns>string(json)</returns>
        public async Task<string> Auth()
            => await Send("auth");

        /// <summary>
        /// Получение платёжных карт
        /// </summary>
        /// <returns>string(json)</returns>
        public async Task<string> Cards()
            => await Send("cards");

        /// <summary>
        /// Пополнение баланса
        /// </summary>
        /// <param name="sum">Сумма пополнения</param>
        /// <param name="cardId">Идентификационный номер карты</param>
        /// <returns>string(json)</returns>
        public async Task<string> AddBalance(int sum, int cardId)
            => await Send($"sum={sum}&cardId={cardId}", "balance/add");

        /// <summary>
        /// Запрос тарифа
        /// </summary>
        /// <returns>string(json)</returns>
        public async Task<string> Tariffs()
            => await Send("tariffs");

        /// <summary>
        /// Добавление подписи
        /// </summary>
        /// <param name="name">Имя подписи</param>
        /// <returns>string(json)</returns>
        public async Task<string> SignAdd(string name)
            => await Send($"name={name}", "sign/add");

        /// <summary>
        /// Получить список подписей
        /// </summary>
        /// <returns>string(json)</returns>
        public async Task<string> SignList()
            => await Send("sign/list");

        /// <summary>
        /// Добавление группы
        /// </summary>
        /// <param name="name">Имя группы</param>
        /// <returns>string(json)</returns>
        public async Task<string> GroupAdd(string name)
            => await Send($"name={UrlEncode(name)}", "group/add");

        /// <summary>
        /// Удаление группы
        /// </summary>
        /// <param name="id">Идентификатор группы</param>
        /// <returns>string(json)</returns>
        public async Task<string> GroupDelete(int id)
            => await Send($"id={id}", "group/delete");

        /// <summary>
        /// Получение списка групп
        /// </summary>
        /// <param name="page">Пагинация</param>
        /// <returns>string(json)</returns>
        public async Task<string> GroupList(int page = 0)
            => await Send(page == 0 ? "" : $"page={page}", "group/list");

        /// <summary>
        /// Добавление контакта
        /// </summary>
        /// <param name="number">Номер абонента</param>
        /// <param name="groupId">Идентификатор группы</param>
        /// <param name="birthday">Дата рождения абонента</param>
        /// <param name="sex">Пол</param>
        /// <param name="lname">Фамилия абонента</param>
        /// <param name="fname">Имя абонента</param>
        /// <param name="sname">Отчество абонента</param>
        /// <param name="param1">Свободный параметр</param>
        /// <param name="param2">Свободный параметр</param>
        /// <param name="param3">Свободный параметр</param>
        /// <returns>string(json)</returns>
        public async Task<string> ContactAdd(string number, int? groupId = null, DateTime? birthday = null, Sex? sex = null,
            string lname = "", string fname = "", string sname = "", string param1 = "", string param2 = "",
            string param3 = "")
        {
            var data = $"number={number}";

            if (groupId != null) data += $"&groupId={groupId}";
            if (birthday != null) data += $"&birthday={ToUnixTime((DateTime)birthday)}";
            if (sex != null) data += $"&sex={sex.ToString()}";
            if (lname != "") data += $"&lname={lname}";
            if (fname != "") data += $"&fname={fname}";
            if (sname != "") data += $"&sname={sname}";
            if (param1 != "") data += $"&param1={UrlEncode(param1)}";
            if (param2 != "") data += $"&param2={UrlEncode(param2)}";
            if (param3 != "") data += $"&param3={UrlEncode(param3)}";

            return await Send(data, "contact/add");
        }

        /// <summary>
        /// Удаление контакта
        /// </summary>
        /// <param name="id">Идентификатор абонента</param>
        /// <returns>string(json)</returns>
        public async Task<string> ContactDelete(int id)
            => await Send($"id={id}", "contact/delete");

        /// <summary>
        /// Список контактов
        /// </summary>
        /// <param name="number">Номер абонента</param>
        /// <param name="groupId">Идентификатор группы</param>
        /// <param name="birthday">Дата рождения абонента</param>
        /// <param name="sex">Пол</param>
        /// <param name="lname">Фамилия абонента</param>
        /// <param name="fname">Имя абонента</param>
        /// <param name="sname">Отчество абонента</param>
        /// <returns>string(json)</returns>
        public async Task<string> ContactList(string number = "", int? groupId = null, DateTime? birthday = null, Sex? sex = null,
            string lname = "", string fname = "", string sname = "")
        {
            var data = "";

            if (number != "") data += $"number={number}&";
            if (groupId != null) data += $"groupId={groupId}&";
            if (birthday != null) data += $"birthday={ToUnixTime((DateTime)birthday)}&";
            if (sex != null) data += $"sex={sex.ToString()}&";
            if (lname != "") data += $"lname={lname}&";
            if (fname != "") data += $"fname={fname}&";
            if (sname != "") data += $"sname={sname}";

            return await Send(data, "contact/list");
        }

        /// <summary>
        /// Добавление в чёрный список
        /// </summary>
        /// <param name="number">Номер абонента</param>
        /// <param name="numbers">Номера телефонов</param>
        /// <returns></returns>
        public async Task<string> BlackListAdd(string number = "", string[]? numbers = null)
        {
            var data = "";

            if (number != "") data += $"number={number}&";
            if (numbers != null) data += numbers.Aggregate("", (s, s1) => $"{s}numbers[]={s1}&");

            return await Send(data, "blacklist/add");
        }

        /// <summary>
        /// Удаление из черного списка
        /// </summary>
        /// <param name="id">Идентификатор абонента</param>
        /// <returns>string(json)</returns>
        public async Task<string> BlackListDelete(int id)
            => await Send($"id={id}", "blacklist/delete");

        /// <summary>
        /// Список контактов в черном списке
        /// </summary>
        /// <param name="number">Номер абонента</param>
        /// <param name="page">Пагинация</param>
        /// <returns>string(json)</returns>
        public async Task<string> BlackListGet(string number = "", int page = 0)
        {
            var data = "";

            if (number != "") data += $"number={number}&";
            if (page > 0) data += $"page={page}&";

            return await Send(data, "blacklist/list");
        }

        // ReSharper disable once InconsistentNaming
        /// <summary>
        /// Создание запроса на проверку HLR
        /// </summary>
        /// <param name="number">Номер абонента</param>
        /// <param name="numbers">Номера телефонов</param>
        /// <returns>string(json)</returns>
        public async Task<string> CheckHLR(string number = "", string[]? numbers = null)
        {
            var data = "";

            if (number != "") data += $"number={number}&";
            if (numbers != null) data += numbers.Aggregate("", (s, s1) => $"{s}numbers[]={s1}&");

            return await Send(data, "hlr/status");
        }

        /// <summary>
        /// Определение оператора
        /// </summary>
        /// <param name="number">Номер абонента</param>
        /// <param name="numbers">Номера телефонов</param>
        /// <returns>string(json)</returns>
        public async Task<string> NumberOperator(string number = "", string[]? numbers = null)
        {
            var data = "";

            if (number != "") data += $"number={number}&";
            if (numbers != null) data += numbers.Aggregate("", (s, s1) => $"{s}numbers[]={s1}&");

            return await Send(data, "number/operator");
        }

        /// <summary>
        /// Отправка Viber-рассылок
        /// </summary>
        /// <param name="channel">Канал отправки Viber</param>
        /// <param name="text">Текст сообщения</param>
        /// <param name="number">Номер телефона</param>
        /// <param name="numbers">Номера телефонов. Максимальное количество 50</param>
        /// <param name="groupId">ID группы по которой будет произведена рассылка. Для выбора всех контактов необходимо передать значение "all"</param>
        /// <param name="imageSource">Картинка кодированная в base64 формат, не должна превышать размер 300 kb. Отправка поддерживается только в 3 форматах: png, jpg, gif. Перед кодированной картинкой необходимо указывать её формат. Пример: jpg#TWFuIGlzIGRpc3Rpbmd1aXNoZ. Отправка доступна только методом POST. 
        ///     Параметр передается совместно с textButton и linkButton</param>
        /// <param name="textButton">Текст кнопки. Максимальная длина 30 символов. Параметр передается совместно с imageSource и linkButton</param>
        /// <param name="linkButton">Ссылки для перехода при нажатие кнопки. Ссылка должна быть с указанием http:// или https://. Параметр передается совместно с imageSource и textButton</param>
        /// <param name="dateSend">Дата для отложенной отправки рассылки</param>
        /// <param name="signSms">Подпись для SMS-рассылки. Используется при выборе канала "Viber-каскад" (channel=CASCADE). Параметр обязателен</param>
        /// <param name="channelSms">Канал отправки SMS-рассылки. Используется при выборе канала "Viber-каскад" (channel=CASCADE). Параметр обязателен</param>
        /// <param name="textSms">Текст сообщения для SMS-рассылки. Используется при выборе канала "Viber-каскад" (channel=CASCADE). Параметр обязателен</param>
        /// <param name="priceSms">Максимальная стоимость SMS-рассылки. Используется при выборе канала "Viber-каскад" (channel=CASCADE). Если параметр не передан, максимальная стоимость будет рассчитана автоматически</param>
        /// <param name="sign">Подпись отправителя</param>
        /// <returns>string(json)</returns>
        public async Task<string> ViberSend(ViberChannel channel, string text, string number = "",
            string[]? numbers = null, string groupId = "", string imageSource = "", string textButton = "",
            string linkButton = "", DateTime? dateSend = null, string signSms = "", string channelSms = "",
            string textSms = "", int? priceSms = null, string sign = "")
        {
            var data = $"sign={(sign == "" ? _from : sign)}&channel={channel.ToString()}&text={UrlEncode(text)}";

            if (number != "") data += $"&number={number}";
            if (numbers != null) data += numbers.Aggregate("", (s, s1) => $"{s}&numbers[]={s1}");
            if (groupId != "") data += $"&groupId={groupId}";
            if (imageSource != "") data += $"&imageSource={imageSource}";
            if (textButton != "") data += $"&textButton={UrlEncode(textButton)}";
            if (linkButton != "") data += $"&linkButton={UrlEncode(linkButton)}";
            if (dateSend != null) data += $"&dateSend={ToUnixTime((DateTime)dateSend)}";
            if (signSms != "") data += $"&signSms={signSms}";
            if (channelSms != "") data += $"&channelSms={channelSms}";
            if (textSms != "") data += $"&textSms={textSms}";
            if (priceSms != null) data += $"&priceSms={priceSms}";

            return await Send(data, "viber/send", HttpMethod.Post);
        }

        /// <summary>
        /// Статистика по Viber-рассылке
        /// </summary>
        /// <param name="sendingId">Идентификатор Viber-рассылки в системе</param>
        /// <returns>string(json)</returns>
        public async Task<string> ViberStatistic(int sendingId)
        {
            var data = $"sendingId={sendingId}";
            return await Send(data, "viber/statistic");
        }

        /// <summary>
        /// Список Viber-рассылок
        /// </summary>
        /// <returns>string(json)</returns>
        public async Task<string> ViberList()
            => await Send("viber/list");

        /// <summary>
        /// Список доступных подписей для Viber-рассылок
        /// </summary>
        /// <returns>string(json)</returns>
        public async Task<string> ViberSignList()
            => await Send("viber/sign/list");

        #region enums

        public enum ViberChannel
        {
            // ReSharper disable once InconsistentNaming
            /// <summary>
            /// Инфоподпись
            /// </summary>
            INFO,

            // ReSharper disable once InconsistentNaming
            /// <summary>
            /// Официальный Viber
            /// </summary>
            OFFICIAL,

            // ReSharper disable once InconsistentNaming
            /// <summary>
            /// Viber каскад
            /// </summary>
            CASCADE
        }

        public enum Sex
        {
            // ReSharper disable once InconsistentNaming
            male,
            // ReSharper disable once InconsistentNaming
            female
        }

        #endregion

        #region ПРИВАТНЫЕ МЕТОДЫ

        /// <summary>
        /// вывод сообщений в консоль
        /// </summary>
        private static void _PrintDebug(string url, string str)
        {
            Console.WriteLine(url);
            Console.WriteLine(str);
            Console.ReadLine();
        }

        /// <summary>
        /// отправка
        /// </summary>
        /// <param name="method">API метод</param>
        /// <returns>string(json)</returns>
        private async Task<string> Send(string method)
            => await Send("", method);

        /// <summary>
        /// Отправка
        /// </summary>
        /// <param name="data">передаваемые данные</param>
        /// <param name="method">API метод</param>
        /// <param name="httpMethod">POST или GET запрос</param>
        /// <returns>string(json)</returns>
        private async Task<string> Send(string data, string method, HttpMethod? httpMethod = null)
        {
            var url = "https://gate.smsaero.ru/v2/" + method;

            // формируем запрос
            HttpRequestMessage? request = null;

            if (httpMethod == null || httpMethod == HttpMethod.Get)
                request = new HttpRequestMessage(HttpMethod.Get, url + "?" + data);

            if (httpMethod == HttpMethod.Post)
                request = new HttpRequestMessage(httpMethod, url)
                {
                    Content = new StringContent(data)
                };

            if (request == null)
                throw new Exception($"{nameof(httpMethod)} value exception");

            // асинхронно отправляем запрос
            var response = await _http.SendAsync(request);

            // проверяем статус код ответа, если не 200 бросаем исключение
            response.EnsureSuccessStatusCode();

            // асинхронно читаем тело ответа сервера
            var responseBody = await response.Content.ReadAsStringAsync();

            // если отладка включена пишем в консоль ответ сервера
            if (Debug) _PrintDebug(url, responseBody);

            return responseBody;
        }

        private static int ToUnixTime(DateTime time)
        {
            return (int)(time - new DateTime(1970, 1, 1)).TotalSeconds;
        }

        private string UrlEncode(string data)
        {
            return HttpUtility.UrlEncode(data);
        }

        #endregion
    }
}
