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
		/// номер телефона игрока
		/// </summary>
		public string? PlayerPhoneNumber { get; set; }

		/// <summary>
		/// Игровое амплуа
		/// Ниже представлены степени двойки, то есть значение для ЦФРВ - 2^0 = 1
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
		[Range(0, 2047, ErrorMessage = "Некорректное амплуа")]
		public int FootballPosition { get; set; }

		/// <summary>
		/// Предпочтительные турниры
		/// Ниже представлены степени двойки, то есть значение для Второй и Первой лиг - 2^6 + 2^7 = 192
		/// 0 - Осенний кубок
		/// 1 - Футзал Молодежная лига
		/// 2 - Футзал Вторая лига
		/// 3 - Футзал Первая лига
		/// 4 - Футзал Высшая лига
		/// 5 - Молодежная лига
		/// 6 - Вторая лига
		/// 7 - Первая лига
		/// 8 - Высшая лига
		/// 9 - Летний кубок
		/// </summary>
		[Range(0, 1023, ErrorMessage = "Некорректные турниры")]
		public int PreferredTournaments { get; set; }

        /// <summary>
        /// Факультет, курс (для выпускников - год выпуска)
        /// </summary>
        [StringLength(101, ErrorMessage = "Слишком много символов")]
        public string? Faculty { get; set; }

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
        /// Контактная информация
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Контактная информация должна быть от 2 до 40 символов")]
        public string? Contact { get; set; }

        /// <summary>
        /// ФИО
        /// </summary>
        [StringLength(61, MinimumLength = 3, ErrorMessage = "ФИО должно быть от 3 до 60 символов")]
        [RegularExpression(@"[ А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В ФИО могут присутствовать только буквы и пробелы")]
        public string? Name { get; set; }

        /// <summary>
        /// Фото профиля
        /// </summary>
        public string? Photo { get; set; }

        /// <summary>
        /// Показывает возможное наличие нецензурной лексики
        /// </summary>
        public bool Attention { get; set; }
    }
}
