using Microsoft.IdentityModel.Tokens;
using System.Text;

namespace HSE_Football_Backend.Auth
{
    /// <summary>
    /// Опции аутентификации
    /// </summary>
    public class AuthOptions
    {
        /// <summary>
        /// Издатель токена
        /// </summary>
        public const string ISSUER = "HSEFootballServer";

        /// <summary>
        /// Потребитель токена
        /// </summary>
        public const string AUDIENCE = "HSEFootballClient";

        /// <summary>
        /// Ключ для шифрования
        /// </summary>
        const string KEY = "mysupersecret_secretkey!123";

        /// <summary>
        /// Время жизни токена - 60 минут
        /// </summary>
        public const int LIFETIME = 60;

        /// <summary>
        /// Генерирует ключ безопасности
        /// </summary>
        /// <returns>Ключ безопасности</returns>
        public static SymmetricSecurityKey GetSymmetricSecurityKey()
        {
            return new SymmetricSecurityKey(Encoding.ASCII.GetBytes(KEY));
        }
    }
}