using HSE_Football_Backend.Models;

namespace HSE_Football_Backend.Auth
{
    /// <summary>
    /// Интерфейс генерации токенов
    /// </summary>
    public interface IJWTManager
    {
        Tokens Authenticate(ref Player player);
        Tokens GenerateJWTToken(Player player);
    }
}