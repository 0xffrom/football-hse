using System.ComponentModel.DataAnnotations;

namespace HSE_Football_Backend.Models
{
    /// <summary>
    /// Игрок
    /// </summary>
    public class Player
    {
        /// <summary>
        /// ID игрока
        /// </summary>
        [Key]
        public long Id { get; set; }

        /// <summary>
        /// Имя
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Имя должно быть от 2 до 40 символов")]
        [RegularExpression(@"[А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В имени могут присутствовать только буквы")]
        public string? Name { get; set; }

        /// <summary>
        /// Фамилия
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Фамилия должна быть от 2 до 40 символов")]
        [RegularExpression(@"[А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В фамилии могут присутствовать только буквы")]
        public string? Surname { get; set; }

        /// <summary>
        /// Отчество
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Отчество должно быть от 2 до 40 символов")]
        [RegularExpression(@"[А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В отчестве могут присутствовать только буквы")]
        public string? SecondName { get; set; }

        /// <summary>
        /// Является ли игрок капитаном
        /// </summary>
        public bool IsCaptain { get; set; } = false;

        /// <summary>
        /// О себе
        /// </summary>
        [StringLength(201, ErrorMessage = "Слишком много символов")]
        public string? About { get; set; }

        /// <summary>
        /// Ссылка на мессенджер (telegram или vk)
        /// </summary>
        [RegularExpression(@"(vk.com/|t.me/)+[-A-Za-z_.0-9]+$", ErrorMessage = "Некорректный формат ссылки")]
        public string? Contact { get; set; }

        /// <summary>
        /// Номер телефона
        /// </summary>
        [Required]
        [RegularExpression(@"^([9]{1}[0-9]{9})?$", ErrorMessage = "Некорректный формат номера телефона")]
        public string? PhoneNumber { get; set; }

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
        /// URL фото профиля
        /// </summary>
        public string? PhotoURL { get; set; }

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
        public string? RefreshToken { get; set; }

        /// <summary>
        /// Время до которого будет существовать указанный токен обновления
        /// </summary>
        public DateTime RefreshTokenExpiryTime { get; set; }

        /// <summary>
        /// Код
        /// </summary>
        [StringLength(129, MinimumLength = 4, ErrorMessage = "код должен быть от 4 до 128 символов")]
        public string? Code { get; set; }

        /// <summary>
        /// Дополнительная секьюрность для хранения кода
        /// </summary>
        public byte[]? SaltForCode { get; set; }
    }
}
