using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HSE_Football_Backend.Data;
using HSE_Football_Backend.Models;
using HSE_Football_Backend.Other;

namespace HSE_Football_Backend.Controllers
{
	/// <summary>
	/// Контроллер заявок игроков
	/// </summary>
	[Route("api/[controller]")]
	[ApiController]
	public class PlayerApplicationsController : ControllerBase
	{
		/// <summary>
		/// Контекст базы данных
		/// </summary>
		private readonly HSEFootballContext _context;

		/// <summary>
		/// Конструктор
		/// </summary>
		/// <param name="context">Контекст БД</param>
		public PlayerApplicationsController(HSEFootballContext context)
		{
			_context = context;
		}

		/// <summary>
		/// Возвращает все заявки игроков
		/// </summary>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы заявок игроков</response>
		// GET: api/PlayerApplications
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[HttpGet]
		public async Task<ActionResult<IEnumerable<PlayerApplication>>> GetPlayerApplications()
		{
			if (_context.PlayerApplications == null)
			{
				return NotFound();
			}
			return await _context.PlayerApplications.ToListAsync();
		}

		/// <summary>
		/// Возвращает заявку игрока по ID
		/// </summary>
		/// <param name="id">ID заявки игрока</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы заявок игроков или нет такой заявки игрока</response>
		// GET: api/PlayerApplications/5
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[HttpGet("{id}")]
		public async Task<ActionResult<PlayerApplication>> GetPlayerApplication(long id)
		{
			if (_context.PlayerApplications == null)
			{
				return NotFound();
			}
			var playerApplication = await _context.PlayerApplications.FindAsync(id);
			if (playerApplication == null)
			{
				return NotFound();
			}
			return playerApplication;
		}

		/// <summary>
		/// Обновляет данные заявки игрока
		/// </summary>
		/// <param name="id">ID заявки игрока</param>
		/// <param name="playerApplication">Заявка игрока</param>
		/// <response code="204">ОК</response>
		/// <response code="404">В БД нет такой заявки игрока</response>
		/// <response code="400">Переданный ID и ID заявки игрока не совпадают</response>
		// PUT: api/PlayerApplications/5
		[ProducesResponseType(204)]
		[ProducesResponseType(404)]
		[ProducesResponseType(400)]
		[HttpPut("{id}")]
		public async Task<IActionResult> PutPlayerApplication(long id, PlayerApplication playerApplication)
		{
			if (id != playerApplication.Id)
			{
				return BadRequest();
			}
			_context.Entry(playerApplication).State = EntityState.Modified;
			try
			{
				await _context.SaveChangesAsync();
			}
			catch (DbUpdateConcurrencyException)
			{
				if (!PlayerApplicationExists(id))
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
		/// Создает заявку игрока
		/// </summary>
		/// <param name="playerApplication">Заявка игрока</param>
		/// <response code="201">ОК</response>
		/// <response code="409">Уже есть заявка игрока с таким ID</response>
		/// <response code="500">>В БД нет таблицы заявок игроков</response>
		// POST: api/PlayerApplications
		[ProducesResponseType(201)]
		[ProducesResponseType(409)]
		[ProducesResponseType(500)]
		[HttpPost]
		public async Task<ActionResult<PlayerApplication>> PostPlayerApplication(PlayerApplication playerApplication)
		{
			if (_context.PlayerApplications == null)
			{
				return Problem("Entity set 'HSEFootballContext.PlayerApplications'  is null.");
			}
			playerApplication.Attention = Censorship.IsCensored(playerApplication.Faculty) ||
										  Censorship.IsCensored(playerApplication.FootballExperience) ||
										  Censorship.IsCensored(playerApplication.TournamentExperience);
			_context.PlayerApplications.Add(playerApplication);
			await _context.SaveChangesAsync();
			return CreatedAtAction("GetPlayerApplication", new { id = playerApplication.Id }, playerApplication);
		}

		/// <summary>
		/// Удаляет заявку игрока
		/// </summary>
		/// <param name="id">ID заявки игрока</param>
		/// <response code="204">ОК</response>
		/// <response code="404">В БД нет таблицы заявок игроков или нет такой заявки игрока</response>
		// DELETE: api/PlayerApplications/5
		[ProducesResponseType(204)]
		[ProducesResponseType(404)]
		[HttpDelete("{id}")]
		public async Task<IActionResult> DeletePlayerApplication(long id)
		{
			if (_context.PlayerApplications == null)
			{
				return NotFound();
			}
			var playerApplication = await _context.PlayerApplications.FindAsync(id);
			if (playerApplication == null)
			{
				return NotFound();
			}
			_context.PlayerApplications.Remove(playerApplication);
			await _context.SaveChangesAsync();
			return NoContent();
		}

		/// <summary>
		/// Возвращает заявки игроков по фильтрам
		/// </summary>
		/// <param name="position">Позиция</param>
		/// <param name="tournaments">Турниры</param>
		/// <response code="200">ОК</response>
		// GET: api/PlayerApplications/filters?position=1&tournaments=1
		[ProducesResponseType(200)]
		[HttpGet("filters")]
		public async Task<ActionResult<IEnumerable<PlayerApplication>>> GetFilteredPlayerApplications(int position = -1, int tournaments = -1)
		{
			var applications = await _context.PlayerApplications.ToListAsync();
			if (position != -1)
				applications = applications.FindAll(a => (a.FootballPosition & position) > 0);
			if (tournaments != -1)
				applications = applications.FindAll(a => (a.PreferredTournaments & tournaments) > 0);
			return applications;
		}

		/// <summary>
		/// Существует ли заявка игрока
		/// </summary>
		/// <param name="id">ID заявки игрока</param>
		private bool PlayerApplicationExists(long id)
		{
			return (_context.PlayerApplications?.Any(e => e.Id == id)).GetValueOrDefault();
		}
	}
}
