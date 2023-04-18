using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HSE_Football_Backend.Data;
using HSE_Football_Backend.Models;

namespace HSE_Football_Backend.Controllers
{
	/// <summary>
	/// Контроллер игроков
	/// </summary>
	[Route("api/[controller]")]
	[ApiController]
	public class PlayersController : ControllerBase
	{
		/// <summary>
		/// Контекст базы данных
		/// </summary>
		private readonly HSEFootballContext _context;

		/// <summary>
		/// Конструктор
		/// </summary>
		/// <param name="context">Контекст БД</param>
		public PlayersController(HSEFootballContext context)
		{
			_context = context;
		}

		/// <summary>
		/// Возвращает всех игроков
		/// </summary>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы игроков</response>
		// GET: api/Players
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[HttpGet]
		public async Task<ActionResult<IEnumerable<Player>>> GetPlayers()
		{
			if (_context.Players == null)
			{
				return NotFound();
			}
			return await _context.Players.ToListAsync();
		}

		/// <summary>
		/// Возвращает игрока по номеру телефона
		/// </summary>
		/// <param name="phone">Номер телефона</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы игроков или нет такого игрока</response>
		// GET: api/Players/89169307114
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[HttpGet("{phone}")]
		public async Task<ActionResult<Player>> GetPlayer(string phone)
		{
			if (_context.Players == null)
			{
				return NotFound();
			}
			var player = await _context.Players.FindAsync(phone);
			if (player == null)
			{
				return NotFound();
			}
			return player;
		}

		/// <summary>
		/// Обновляет данные игрока
		/// </summary>
		/// <param name="phone">Номер телефона</param>
		/// <param name="player">Игрок</param>
		/// <response code="204">ОК</response>
		/// <response code="404">В БД нет такого игрока</response>
		/// <response code="400">Переданный номер и номер игрока не совпадают</response>
		// PUT: api/Players/89169307114
		[ProducesResponseType(204)]
		[ProducesResponseType(404)]
		[ProducesResponseType(400)]
		[HttpPut("{phone}")]
		public async Task<IActionResult> PutPlayer(string phone, Player player)
		{
			if (phone != player.PhoneNumber)
			{
				return BadRequest();
			}
			_context.Entry(player).State = EntityState.Modified;
			try
			{
				await _context.SaveChangesAsync();
			}
			catch (DbUpdateConcurrencyException)
			{
				if (!PlayerExists(phone))
				{
					return NotFound();
				}
				else
				{
					throw;
				}
			}
			return NoContent();
		}

		/// <summary>
		/// Создает игрока
		/// </summary>
		/// <param name="player">Игрок</param>
		/// <response code="201">ОК</response>
		/// <response code="409">Уже есть игрок с таким номером</response>
		/// <response code="500">>В БД нет таблицы игроков</response>
		// POST: api/Players
		[ProducesResponseType(201)]
		[ProducesResponseType(409)]
		[ProducesResponseType(500)]
		[HttpPost]
		public async Task<ActionResult<Player>> PostPlayer(Player player)
		{
			if (_context.Players == null)
			{
				return Problem("Entity set 'HSEFootballContext.Players'  is null.");
			}
			_context.Players.Add(player);
			try
			{
				await _context.SaveChangesAsync();
			}
			catch (DbUpdateException)
			{
				if (PlayerExists(player.PhoneNumber))
				{
					return Conflict();
				}
				else
				{
					throw;
				}
			}
			return CreatedAtAction("GetPlayer", new { phone = player.PhoneNumber }, player);
		}

		/// <summary>
		/// Удаляет игрока
		/// </summary>
		/// <param name="phone">Номер телефона</param>
		/// <response code="204">ОК</response>
		/// <response code="404">В БД нет таблицы игроков или нет такого игрока</response>
		// DELETE: api/Players/89169307114
		[ProducesResponseType(204)]
		[ProducesResponseType(404)]
		[HttpDelete("{phone}")]
		public async Task<IActionResult> DeletePlayer(string phone)
		{
			if (_context.Players == null)
			{
				return NotFound();
			}
			var player = await _context.Players.FindAsync(phone);
			if (player == null)
			{
				return NotFound();
			}
			_context.Players.Remove(player);
			await _context.SaveChangesAsync();
			return NoContent();
		}

		/// <summary>
		/// Существует ли игрок
		/// </summary>
		/// <param name="phone">Номер телефона</param>
		private bool PlayerExists(string phone)
		{
			return (_context.Players?.Any(e => e.PhoneNumber == phone)).GetValueOrDefault();
		}
	}
}
