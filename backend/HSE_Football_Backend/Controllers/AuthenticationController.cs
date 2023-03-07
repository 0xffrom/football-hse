using HSE_Football_Backend.Auth;
using HSE_Football_Backend.Data;
using HSE_Football_Backend.Libs;
using HSE_Football_Backend.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Security.Cryptography;

namespace HSE_Football_Backend.Controllers
{
    /// <summary>
    /// Контроллер аутентификации
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class AuthenticationController : Controller
    {
        /// <summary>
        /// Генератор случайных чисел для генерации кода подтверждения
        /// </summary>
        private readonly Random rnd = new Random();

        /// <summary>
        /// Сущность управления JWT-токенами
        /// </summary>
        private readonly IJWTManager _jWTManager;

        /// <summary>
        /// Контекст базы данных
        /// </summary>
        private readonly HSEFootballContext _context;

        /// <summary>
        /// Конструктор
        /// </summary>
        /// <param name="context">Контекст базы данных</param>
        /// <param name="jWTManager">Сущность управления JWT-токенами</param>
        public AuthenticationController(HSEFootballContext context, IJWTManager jWTManager)
        {
            _context = context;
            _jWTManager = jWTManager;
        }

        /// <summary>
        /// Получение токена обновления
        /// </summary>
        /// <param name="number">Номер телефона</param>
        /// <param name="code">Код</param>
        /// <response code="200">ОК</response>
        /// <response code="401">Нет пользователя или Токен обновления устарел</response>
        [ProducesResponseType(200)]
        [ProducesResponseType(401)]
        [HttpPost("refresh/{number}/{code}")]
        // POST: api/Authentication/refresh/89169307114/1234
        public async Task<IActionResult> GetRefreshToken(string number, string code)
        {
            Player playerData = _context.Players.FirstOrDefault(x => x.PhoneNumber == number);

            if (playerData == null || IsCodeIncorrect(playerData, code))
            {
                return Unauthorized();
            }

            var token = _jWTManager.Authenticate(ref playerData);

            _context.Entry(playerData).State = EntityState.Modified;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(number))
                    return NotFound();
                else
                    throw;
            }

            return Ok(token);
        }

        /// <summary>
        /// Получение токена доступа
        /// </summary>
        /// <param name="data">Предыдущий полученный токен. Включает в себя сам токен, токен обновления, номер телефона, является ли капитаном</param>
        /// <response code="200">ОК</response>
        /// <response code="401">Нет пользователя или Токен обновления устарел</response>
        [ProducesResponseType(200)]
        [ProducesResponseType(401)]
        [HttpPost("access")]
        // POST: api/Authentication/access
        public IActionResult GetAccessToken(Tokens data)
        {
            Player playerData = _context.Players.FirstOrDefault(x => x.PhoneNumber == data.PhoneNumber && x.RefreshToken == data.RefreshToken);
            if (playerData == null || playerData.RefreshTokenExpiryTime <= DateTime.UtcNow)
            {
                return Unauthorized();
            }
            var token = _jWTManager.GenerateJWTToken(playerData);
            token.RefreshToken = data.RefreshToken;

            return Ok(token);
        }

        /// <summary>
        /// Аутентификация по номеру телефона
        /// </summary>
        /// <param name="number">Номер телефона</param>
        /// <response code="200">Код отправлен в смс</response>
        [ProducesResponseType(200)]
        [HttpPost("phone/{number}")]
        // POST: api/Authentication/phone/89169307114
        public async Task<IActionResult> AuthByPhone(string number)
        {
            Player playerData = _context.Players.FirstOrDefault(x => x.PhoneNumber == number);
            if (playerData == null)
            {
                playerData = new Player() { PhoneNumber = number };
                _context.Players.Add(playerData);
            }

            // Генерация кода подтверждения
            //playerData.Code = rnd.Next(1000, 10000).ToString();

            //// Отправка смс с кодом
            //SmsAero smsc = new SmsAero("ckd001@mail.ru", "foR-O2fmlGrCjzzD-dF-x3cBLIbNmqAF");
            //await smsc.SmsSend($"Ваш код подтверждения для футбола ВШЭ: {playerData.Code}", number);

            playerData.Code = "1111";

            // Генерация добавки для хеширования кода
            GenerateSalt(ref playerData);
            // Хеширование кода
            playerData.Code = Hash(playerData.SaltForCode, playerData.Code);

            await _context.SaveChangesAsync();
            return Ok();
        }

        /// <summary>
        /// Хеширование кода
        /// </summary>
        /// <param name="salt">Добавка для хеша</param>
        /// <param name="code">Хешируемый код</param>
        /// <returns>Хеш кода</returns>
        private static string Hash(byte[] salt, string code)
        {
            return Convert.ToBase64String(KeyDerivation.Pbkdf2(
                password: code, // хешируемый код
                salt: salt, // добавка для секьюрности хеша
                prf: KeyDerivationPrf.HMACSHA256, // Хеш (SHA256 вполне хватает для нужд хранения кода)
                iterationCount: 100000, // количество хеш-итераций
                numBytesRequested: 256 / 8)); // желаемая длина выходного ключа
        }

        /// <summary>
        /// Проверка корректности переданного кода
        /// </summary>
        /// <param name="player">Сущность пользователя, для которой проверяется код</param>
        /// <param name="code">Проверяемый код</param>
        /// <returns>1 - если пароль не соответствует захешированному в сущности, 0 - иначе</returns>
        private static bool IsCodeIncorrect(Player player, string code)
        {
            var hashedCode = Hash(player.SaltForCode, code);
            return player.Code != hashedCode;
        }

        /// <summary>
        /// Проверка наличия пользователя
        /// </summary>
        /// <param name="numver">Номер телефона</param>
        /// <returns>Существует ли такой пользователь</returns>
        private bool UserExists(string number)
        {
            return _context.Players.Any(e => e.PhoneNumber == number);
        }

        /// <summary>
        /// Генерация добавки ("соли") к коду для надежности хеша
        /// </summary>
        /// <param name="player">Сущность пользователя, в которую добавляется соль</param>
        private static void GenerateSalt(ref Player player)
        {
            byte[] salt = new byte[128 / 8];
            using (var rngCsp = new RNGCryptoServiceProvider())
            {
                rngCsp.GetNonZeroBytes(salt);
            }

            player.SaltForCode = salt;
        }
    }
}
