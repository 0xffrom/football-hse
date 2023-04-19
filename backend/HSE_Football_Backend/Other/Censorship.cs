namespace HSE_Football_Backend.Other
{
	/// <summary>
	/// Класс для автоматического определения наличия нецензцрной лексики
	/// </summary>
	public class Censorship
	{
		/// <summary>
		/// Нецензурные слова
		/// </summary>
		private static readonly List<string> words = new List<string> {
			"апездал", "апездошенная", "блядь", "блядство", "выебон", "выебать", "членосос", "членоплет", "шлюха",
			"ебунок", "еблан", "ёбнуть", "ёболызнуть", "ебош", "заебал", "мокрощелка", "наебка", "наебал", "наебаловка",
			"напиздеть", "отъебись", "пидарас", "пиздатый", "пиздец", "пизданутый", "поебать", "поебустика", "разъебанный",
			"сука", "сучка", "трахать", "уебок", "уебать", "хуево", "хуесос", "хуеть", "хуевертить", "хуеглот", "хуистика",
			"гомосек", "долбоёб", "ебло", "еблище", "ебать", "вхуюжить", "злаебучий", "заёб", "колдоебина", "манда", "мандовошка",
			"заебатый", "отхуевертить", "опизденеть", "охуевший", "отебукать", "пизда", "охуеть", "подзалупный", "пизденыш",
			"припиздак", "разъебать", "распиздяй", "проебать", "уебан", "хитровыебанный", "хуй", "хуйня", "хуета", "угондошить" 
		};

		/// <summary>
		/// Варианты написания одних и тех же букв
		/// </summary>
		private static readonly Dictionary<char, List<char>> d = new Dictionary<char, List<char>>() {
			{ 'а', new List<char> { 'а', 'a', '@' } },
			{ 'б', new List<char> { 'б', '6', 'b' } },
			{ 'в', new List<char> { 'в', 'b', 'v' } },
			{ 'г', new List<char> { 'г', 'r', 'g' } },
			{ 'д', new List<char> { 'д', 'd' } },
			{ 'е', new List<char> { 'е', 'e' } },
			{ 'ё', new List<char> { 'ё', 'e' } },
			{ 'ж', new List<char> { 'ж', '*' } },
			{ 'з', new List<char> { 'з', '3', 'z' } },
			{ 'и', new List<char> { 'и', 'u', 'i' } },
			{ 'й', new List<char> { 'й', 'u', 'i' } },
			{ 'к', new List<char> { 'к', 'k', } },
			{ 'л', new List<char> { 'л', 'l' } },
			{ 'м', new List<char> { 'м', 'm' } },
			{ 'н', new List<char> { 'н', 'h', 'n' } },
			{ 'о', new List<char> { 'о', 'o', '0' } },
			{ 'п', new List<char> { 'п', 'n', 'p' } },
			{ 'р', new List<char> { 'р', 'r', 'p' } },
			{ 'с', new List<char> { 'с', 'c', 's' } },
			{ 'т', new List<char> { 'т', 'm', 't' } },
			{ 'у', new List<char> { 'у', 'y', 'u' } },
			{ 'ф', new List<char> { 'ф', 'f' } },
			{ 'х', new List<char> { 'х', 'x', 'h' } },
			{ 'ц', new List<char> { 'ц', 'c' } },
			{ 'ч', new List<char> { 'ч' } },
			{ 'ш', new List<char> { 'ш' } },
			{ 'щ', new List<char> { 'щ' } },
			{ 'ь', new List<char> { 'ь', 'b' } },
			{ 'ы', new List<char> { 'ы' } },
			{ 'ъ', new List<char> { 'ъ' } },
			{ 'э', new List<char> { 'э', 'e' } },
			{ 'ю', new List<char> { 'ю' } },
			{ 'я', new List<char> { 'я' } }
		};

		/// <summary>
		/// Метод, определяющий расстояние Левенштейна для двух слов
		/// </summary>
		/// <param name="firstWord">Первое слово</param>
		/// <param name="secondWord">Второе слово</param>
		private static int LevenshteinDistance(string firstWord, string secondWord)
		{
			int n = firstWord.Length + 1;
			int m = secondWord.Length + 1;
			int[,] matrixD = new int[n, m];
			for (int i = 0; i < n; i++)
			{
				matrixD[i, 0] = i;
			}
			for (int j = 0; j < m; j++)
			{
				matrixD[0, j] = j;
			}
			for (int i = 1; i < n; i++)
			{
				for (int j = 1; j < m; j++)
				{
					int cost = firstWord[i - 1] == secondWord[j - 1] ? 0 : 1;
					matrixD[i, j] = Math.Min(Math.Min(matrixD[i - 1, j] + 1, matrixD[i, j - 1] + 1), matrixD[i - 1, j - 1] + cost);
				}
			}
			return matrixD[n - 1, m - 1];
		}

		/// <summary>
		/// Непосредственное определение нецензурной лексики
		/// </summary>
		/// <param name="phrase">Фраза</param>
		public static bool IsCensored(string phrase)
		{
			if (phrase == null) return false;
			// Делаем из фразы новую в нижнем регистре, и с буквами только из русского алфавита
			string newphrase = phrase.ToLower();
			foreach (var letter in d)
			{
				foreach (char l in letter.Value)
				{
					foreach (char p in newphrase)
					{
						if (l == p)
							newphrase = newphrase.Replace(p, letter.Key);
					}
				}
			}
			// Для каждого нецензурного слова проходимся по отрезкам фразы, равным длине этого слова
			foreach (string w in words)
			{
				for (int i = 0; i < newphrase.Length - w.Length + 1; i++)
				{
					string fragment = newphrase.Substring(i, w.Length);
					// Если расстояние Левенштейна для отрезка <= 25% длины слова
					if (LevenshteinDistance(fragment, w) <= w.Length * 0.25)
					{
						// Значит слово нецензурное
						return true;
					}
				}
			}
			return false;
		}
	}
}
