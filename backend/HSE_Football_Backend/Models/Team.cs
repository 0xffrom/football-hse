using System.ComponentModel.DataAnnotations;

namespace HSE_Football_Backend.Models
{
    /// <summary>
    /// Команда
    /// </summary>
    public class Team
    {
        /// <summary>
        /// ID команды
        /// </summary>
        [Key]
        public long Id { get; set; }

        /// <summary>
        /// Название 
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Название должно быть от 2 до 40 символов")]
        [RegularExpression(@"[А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В названии могут присутствовать только буквы")]
        public string? Name { get; set; }

        /// <summary>
        /// ID капитана
        /// </summary>
        public long CaptainId { get; set; }

        /// <summary>
        /// URL логотипа
        /// </summary>
        public string? LogoURL { get; set; }

        /// <summary>
        /// О команде
        /// </summary>
        [StringLength(201, ErrorMessage = "Слишком много символов")]
        public string? About { get; set; }

        /// <summary>
        /// Статус верификации команды
        /// 0 - на рассмотрении
        /// 1 - верифицирована
        /// 2 - отклонена
        /// </summary>
        [Range(0, 2, ErrorMessage = "Некорректный статус верификации")]
        public int Status { get; set; }
    }
}
