using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace HSE_Football_Backend.Models
{
    /// <summary>
    /// Игрок
    /// </summary>
    public class Player
    {
        /// <summary>
        /// Номер телефона
        /// </summary>
        [Required]
        [RegularExpression(@"^([8]{1}[0-9]{10})?$", ErrorMessage = "Некорректный формат номера телефона")]
        [Key]
        public string? PhoneNumber { get; set; }

        /// <summary>
        /// ФИО
        /// </summary>
        [StringLength(61, MinimumLength = 3, ErrorMessage = "ФИО должно быть от 3 до 60 символов")]
        [RegularExpression(@"[ А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В ФИО могут присутствовать только буквы и пробелы")]
        public string? Name { get; set; }

        /// <summary>
        /// Является ли игрок капитаном
        /// </summary>
        public bool IsCaptain { get; set; } = false;

        /// <summary>
        /// Прошел ли игрок первичную регистрацию
        /// </summary>
        public bool IsRegistered { get; set; } = false;

        /// <summary>
        /// О себе
        /// </summary>
        [StringLength(201, ErrorMessage = "Слишком много символов")]
        public string? About { get; set; }

        /// <summary>
        /// Контактная информация
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Контактная информация должна быть от 2 до 40 символов")]
        public string? Contact { get; set; }

        /// <summary>
        /// Футбольный опыт
        /// </summary>
        [StringLength(201, ErrorMessage = "Слишком много символов")]
        public string? FootballExperience { get; set; }

        /// <summary>
        /// Опыт в турнирах ВШЭ
        /// </summary>
        [StringLength(201, ErrorMessage = "Слишком много символов")]
        public string? TournamentExperience { get; set; }

        /// <summary>
        /// Фото профиля
        /// </summary>
        public string? Photo { get; set; }

        /// <summary>
        /// Роль в ВШЭ
        /// 0 - студент
        /// 1 - выпускник
        /// 2 - преподаватель
        /// 3 - легионер
        /// </summary>
        [Range(0, 3, ErrorMessage = "Некорректная роль в ВШЭ")]
        public int HSERole { get; set; }

        /// <summary>
        /// ID заявки
        /// </summary>
        public long ApplicationId { get; set; }

        /// <summary>
        /// Токен обновления токена доступа для учетной записи пользователя
        /// </summary>
        [JsonIgnore]
        public string? RefreshToken { get; set; }

        /// <summary>
        /// Время до которого будет существовать указанный токен обновления
        /// </summary>
        [JsonIgnore]
        public DateTime RefreshTokenExpiryTime { get; set; }

        /// <summary>
        /// Код
        /// </summary>
        [StringLength(129, MinimumLength = 4, ErrorMessage = "код должен быть от 4 до 128 символов")]
        [JsonIgnore]
        public string? Code { get; set; }

        /// <summary>
        /// Дополнительная секьюрность для хранения кода
        /// </summary>
        [JsonIgnore]
        public byte[]? SaltForCode { get; set; }
    }
}
