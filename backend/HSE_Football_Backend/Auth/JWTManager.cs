using Microsoft.IdentityModel.Tokens;
using HSE_Football_Backend.Models;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;

namespace HSE_Football_Backend.Auth
{
    /// <summary>
    /// Класс генерации токенов
    /// </summary>
    public class JWTManager : IJWTManager
    {
        public JWTManager()
        {
        }

        /// <summary>
        ///	Метод полной аутентификации пользователя.
        ///	Создается новый токен доступа и новый токен обновления, 
        ///	Токен обновления записывается в структуру пользователя, которая позже сохранятеся в БД
        /// </summary>
        /// <param name="player">Структура с данными пользователя</param>
        /// <returns>Структура-токен: токен доступа, токен обновления, номер телефона, является ли капитаном</returns>
        public Tokens Authenticate(ref Player player)
        {
            var tokens = GenerateJWTToken(player);
            player.RefreshToken = GenerateRefreshToken();
            player.RefreshTokenExpiryTime = DateTime.UtcNow.AddMonths(2);

            tokens.RefreshToken = player.RefreshToken;

            return tokens;
        }

        /// <summary>
        ///	Метод частичной аутентификации пользователя.
        ///	Создается только новый токен доступа
        /// </summary>
        /// <param name="player">Структура с данными пользователя</param>
        /// <returns>Структура-токен: токен доступа, почта пользователя, является ли капитаном. Токен обновления пустой</returns>
        public Tokens GenerateJWTToken(Player player)
        {
            var identity = GetIdentity(player);
            // создаем JWT-токен
            var jwt = new JwtSecurityToken(
                    issuer: AuthOptions.ISSUER,
                    audience: AuthOptions.AUDIENCE,
                    notBefore: DateTime.UtcNow,
                    claims: identity.Claims,
                    expires: DateTime.UtcNow.AddMinutes(AuthOptions.LIFETIME),
                    signingCredentials: new SigningCredentials(AuthOptions.GetSymmetricSecurityKey(), SecurityAlgorithms.HmacSha256));
            var encodedJwt = new JwtSecurityTokenHandler().WriteToken(jwt);
            return new Tokens { Token = encodedJwt, PhoneNumber = player.PhoneNumber, IsCaptain = player.IsCaptain, IsRegistered = player.IsRegistered };
        }

        /// <summary>
        ///	Метод генерации токена обновления
        /// </summary>
        /// <returns>Строковое представление токена</returns>
        private static string GenerateRefreshToken()
        {
            using var rngCryptoServiceProvider = new RNGCryptoServiceProvider();

            var randomBytes = new byte[64];
            rngCryptoServiceProvider.GetBytes(randomBytes);
            return Convert.ToBase64String(randomBytes);
        }

        /// <summary>
        ///	Метод создания сущности ClaimsIdentity из сущности пользователя
        /// </summary>
        /// <returns>Сущность ClaimsIdentity</returns>
        private static ClaimsIdentity GetIdentity(Player player)
        {
            var claims = new List<Claim>
                {
                    new Claim(ClaimsIdentity.DefaultNameClaimType, player.PhoneNumber)
                };
            ClaimsIdentity claimsIdentity = new(claims, "Token", ClaimsIdentity.DefaultNameClaimType, player.PhoneNumber);
            return claimsIdentity;
        }
    }
}
