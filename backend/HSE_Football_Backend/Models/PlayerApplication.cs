using System.ComponentModel.DataAnnotations;

namespace HSE_Football_Backend.Models
{
    /// <summary>
    /// Заявка игрока
    /// </summary>
    public class PlayerApplication
    {
        /// <summary>
        /// ID заявки игрока
        /// </summary>
        [Key]
        public long Id { get; set; }

        /// <summary>
        /// ID игрока
        /// </summary>
        public long PlayerId { get; set; }

        /// <summary>
        /// Игровое амплуа
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
        public int FootballPosition { get; set; }

        // TODO
        // Для фильтров исп. побитовое "и"
        // Если больше 0, то хотя бы по одному подходит
        // Возможно для амплуа стоит сделать так же
        /// <summary>
        /// Предпочтительные турниры
        /// </summary>
        [Range(0, 10, ErrorMessage = "Некорректные турниры")]
        public int PreferredTournaments { get; set; }
    }
}
