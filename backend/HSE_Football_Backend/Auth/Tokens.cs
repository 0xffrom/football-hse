namespace HSE_Football_Backend.Auth
{
    /// <summary>
    /// Токены
    /// </summary>
    public class Tokens
    {
        /// <summary>
        /// Токен доступа
        /// </summary>
        public string? Token { get; set; }

        /// <summary>
        /// Токен обновления
        /// </summary>
        public string? RefreshToken { get; set; }

        /// <summary>
        /// Номер телефона
        /// </summary>
        public string? PhoneNumber { get; set; }

        /// <summary>
        /// Является ли игрок капитаном
        /// </summary>
        public bool IsCaptain { get; set; }
    }
}
