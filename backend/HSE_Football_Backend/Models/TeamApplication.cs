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
        /// Название 
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Название должно быть от 2 до 40 символов")]
        [RegularExpression(@"[А-Яа-яЁёA-Za-z]+$", ErrorMessage = "В названии могут присутствовать только буквы")]
        public string? Name { get; set; }

        /// <summary>
        /// Логотип
        /// </summary>
        public string? Logo { get; set; }

        /// <summary>
        /// Контактная информация
        /// </summary>
        [StringLength(41, MinimumLength = 2, ErrorMessage = "Контактная информация должна быть от 2 до 40 символов")]
        public string? Contact { get; set; }

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
		public int PlayerPosition { get; set; }

		/// <summary>
		/// Турниры, на которые ищутся игроки
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
		public int Tournaments { get; set; }

		/// <summary>
		/// Описание
		/// </summary>
		[StringLength(201, ErrorMessage = "Слишком много символов")]
		public string? Description { get; set; }

		/// <summary>
		/// Показывает возможное наличие нецензурной лексики
		/// </summary>
		public bool Attention { get; set; }
	}
}
