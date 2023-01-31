using System.ComponentModel.DataAnnotations;

namespace HSE_Football_Backend.Models
{
    /// <summary>
    /// Заявка команды
    /// </summary>
    public class TeamApplication
    {
        /// <summary>
        /// ID заявки команды
        /// </summary>
        [Key]
        public long Id { get; set; }

        /// <summary>
        /// ID команды
        /// </summary>
        public long TeamId { get; set; }

        /// <summary>
        /// Игровое амплуа игрока
        /// 0 - ЦФРВ
        /// 1 - ЛФРВ
        /// 2 - ПФРВ
        /// 3 - ЦАП
        /// 4 - ЦОП
        /// 5 - ЛП
        /// 6 - ПП
        /// 7 - ЦЗ
        /// 8 - ЛЗ
        /// 9 - ПЗ
        /// 10 - ВРТ
        /// </summary>
        [Range(0, 10, ErrorMessage = "Некорректное амплуа")]
        public int PlayerPosition { get; set; }

        // TODO
        // Для фильтров исп. побитовое "и"
        // Если больше 0, то хотя бы по одному подходит
        // Возможно для амплуа стоит сделать так же
        /// <summary>
        /// Турниры, на которые ищутся игроки
        /// </summary>
        [Range(0, 10, ErrorMessage = "Некорректные турниры")]
        public int Tournaments { get; set; }

        /// <summary>
        /// Описание
        /// </summary>
        [StringLength(201, ErrorMessage = "Слишком много символов")]
        public string? Description { get; set; }
    }
}
