using System.ComponentModel.DataAnnotations;

namespace HSE_Football_Backend.Models
{
    /// <summary>
    /// Переписка
    /// </summary>
    public class Chat
    {
        /// <summary>
        /// ID переписки
        /// </summary>
        [Key]
        public long Id { get; set; }

        /// <summary>
        /// Последнее сообщение
        /// </summary>
        public Message? LastMessage { get; set; }

        /// <summary>
        /// Номер телефона первого собеседника
        /// </summary>
        [RegularExpression(@"^([8]{1}[0-9]{10})?$", ErrorMessage = "Некорректный формат номера телефона")]
        public string? PhoneNumber1 { get; set; }

        /// <summary>
        /// ФИО первого собеседника
        /// </summary>
        [StringLength(61, MinimumLength = 3, ErrorMessage = "ФИО должно быть от 3 до 60 символов")]
        [RegularExpression(@"[ А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В ФИО могут присутствовать только буквы и пробелы")]
        public string? Name1 { get; set; }

        /// <summary>
        /// Фото профиля первого собеседника
        /// </summary>
        public string? Photo1 { get; set; }

        /// <summary>
        /// Номер телефона второго собеседника
        /// </summary>
        [RegularExpression(@"^([8]{1}[0-9]{10})?$", ErrorMessage = "Некорректный формат номера телефона")]
        public string? PhoneNumber2 { get; set; }

        /// <summary>
        /// ФИО второго собеседника
        /// </summary>
        [StringLength(61, MinimumLength = 3, ErrorMessage = "ФИО должно быть от 3 до 60 символов")]
        [RegularExpression(@"[ А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В ФИО могут присутствовать только буквы и пробелы")]
        public string? Name2 { get; set; }

        /// <summary>
        /// Фото профиля второго собеседника
        /// </summary>
        public string? Photo2 { get; set; }
    }
}
