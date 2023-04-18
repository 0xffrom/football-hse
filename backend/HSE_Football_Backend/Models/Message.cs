using System.ComponentModel.DataAnnotations;

namespace HSE_Football_Backend.Models
{
    /// <summary>
    /// Сообщение
    /// </summary>
    public class Message
    {
        /// <summary>
        /// ID сообщения
        /// </summary>
        [Key]
        public long Id { get; set; }

        /// <summary>
        /// Время отправки
        /// </summary>
        public string? SendTime { get; set; }

        /// <summary>
        /// Текст
        /// </summary>
        [StringLength(301, ErrorMessage = "Слишком много символов")]
        public string? Text { get; set; }

        /// <summary>
        /// Прочитано ли
        /// </summary>
        public bool IsRead { get; set; }

        /// <summary>
        /// Номер телефона отправителя
        /// </summary>
        [RegularExpression(@"^([8]{1}[0-9]{10})?$", ErrorMessage = "Некорректный формат номера телефона")]
        public string? Sender { get; set; }

        /// <summary>
        /// Номер телефона получателя
        /// </summary>
        [RegularExpression(@"^([8]{1}[0-9]{10})?$", ErrorMessage = "Некорректный формат номера телефона")]
        public string? Receiver { get; set; }
    }
}
