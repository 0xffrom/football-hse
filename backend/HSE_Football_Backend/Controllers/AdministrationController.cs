using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HSE_Football_Backend.Data;
using HSE_Football_Backend.Models;

namespace HSE_Football_Backend.Controllers
{
	/// <summary>
	/// Контроллер панели администрирования
	/// </summary>
	[Route("api/[controller]")]
	[ApiController]
	public class AdministrationController : ControllerBase
	{
		/// <summary>
		/// Контекст базы данных
		/// </summary>
		private readonly HSEFootballContext _context;

		/// <summary>
		/// Логин-пароль панели администрирования
		/// </summary>
		private readonly string password = "adminadmin";

		/// <summary>
		/// Конструктор
		/// </summary>
		/// <param name="context">Контекст БД</param>
		public AdministrationController(HSEFootballContext context)
		{
			_context = context;
		}

		/// <summary>
		/// Авторизация в панель администрирования
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <response code="200">ОК</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/Authorize/adminadmin
		[ProducesResponseType(200)]
		[ProducesResponseType(401)]
		[HttpGet("Authorize/{password}")]
		public async Task<IActionResult> Authorize(string password)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			return Ok();
		}

		/// <summary>
		/// Возвращает игрока по номеру телефона
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="phone">Номер телефона</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы игроков или нет такого игрока</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/Players/89169307114
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/Players/{phone}")]
		public async Task<ActionResult<Player>> GetPlayer(string password, string phone)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
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
		/// Возвращает все заявки игроков
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы заявок игроков</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/PlayerApplications
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/PlayerApplications")]
		public async Task<ActionResult<IEnumerable<PlayerApplication>>> GetPlayerApplications(string password)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			if (_context.PlayerApplications == null)
			{
				return NotFound();
			}
			return await _context.PlayerApplications.ToListAsync();
		}

		/// <summary>
		/// Возвращает заявку игрока по ID
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="id">ID заявки игрока</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы заявок игроков или нет такой заявки игрока</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/PlayerApplications/5
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/PlayerApplications/{id}")]
		public async Task<ActionResult<PlayerApplication>> GetPlayerApplication(string password, long id)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
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
		/// Удаляет заявку игрока
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="id">ID заявки игрока</param>
		/// <response code="204">ОК</response>
		/// <response code="404">В БД нет таблицы заявок игроков или нет такой заявки игрока</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// DELETE: api/Administration/adminadmin/PlayerApplications/5
		[ProducesResponseType(204)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpDelete("{password}/PlayerApplications/{id}")]
		public async Task<IActionResult> DeletePlayerApplication(string password, long id)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
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
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="position">Позиция</param>
		/// <param name="tournaments">Турниры</param>
		/// <response code="200">ОК</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/PlayerApplications/filters?position=1&tournaments=1
		[ProducesResponseType(200)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/PlayerApplications/filters")]
		public async Task<ActionResult<IEnumerable<PlayerApplication>>> GetFilteredPlayerApplications(string password, int position = -1, int tournaments = -1)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			var applications = await _context.PlayerApplications.ToListAsync();
			if (position != -1)
				applications = applications.FindAll(a => (a.FootballPosition & position) > 0);
			if (tournaments != -1)
				applications = applications.FindAll(a => (a.PreferredTournaments & tournaments) > 0);
			return applications;
		}

		/// <summary>
		/// Возвращает все заявки команд
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы заявок команд</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/TeamApplications
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/TeamApplications")]
		public async Task<ActionResult<IEnumerable<TeamApplication>>> GetTeamApplications(string password)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			if (_context.TeamApplications == null)
			{
				return NotFound();
			}
			return await _context.TeamApplications.ToListAsync();
		}

		/// <summary>
		/// Возвращает заявку команды по ID
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="id">ID заявки команды</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы заявок команд или нет такой заявки команды</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/TeamApplications/5
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/TeamApplications/{id}")]
		public async Task<ActionResult<TeamApplication>> GetTeamApplication(string password, long id)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			if (_context.TeamApplications == null)
			{
				return NotFound();
			}
			var teamApplication = await _context.TeamApplications.FindAsync(id);
			if (teamApplication == null)
			{
				return NotFound();
			}
			return teamApplication;
		}

		/// <summary>
		/// Удаляет заявку команды
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="id">ID заявки команды</param>
		/// <response code="204">ОК</response>
		/// <response code="404">В БД нет таблицы заявок команд или нет такой заявки команды</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// DELETE: api/Administration/adminadmin/TeamApplications/5
		[ProducesResponseType(204)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpDelete("{password}/TeamApplications/{id}")]
		public async Task<IActionResult> DeleteTeamApplication(string password, long id)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			if (_context.TeamApplications == null)
			{
				return NotFound();
			}
			var teamApplication = await _context.TeamApplications.FindAsync(id);
			if (teamApplication == null)
			{
				return NotFound();
			}
			_context.TeamApplications.Remove(teamApplication);
			await _context.SaveChangesAsync();
			return NoContent();
		}

		/// <summary>
		/// Возвращает заявки команд по фильтрам
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="position">Позиция</param>
		/// <param name="tournaments">Турниры</param>
		/// <response code="200">ОК</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/TeamApplications/filters?position=1&tournaments=1
		[ProducesResponseType(200)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/TeamApplications/filters")]
		public async Task<ActionResult<IEnumerable<TeamApplication>>> GetFilteredTeamApplications(string password, int position = -1, int tournaments = -1)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			var applications = await _context.TeamApplications.ToListAsync();
			if (position != -1)
				applications = applications.FindAll(a => (a.PlayerPosition & position) > 0);
			if (tournaments != -1)
				applications = applications.FindAll(a => (a.Tournaments & tournaments) > 0);
			return applications;
		}

		/// <summary>
		/// Возвращает команды по фильтрам
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="status">Статус команды</param>
		/// <response code="200">ОК</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/Teams/filters?status=0
		[ProducesResponseType(200)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/Teams/filters")]
		public async Task<ActionResult<IEnumerable<Team>>> GetFilteredTeams(string password, int status = -1)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			var teams = await _context.Teams.ToListAsync();
			if (status != -1)
				teams = teams.FindAll(a => a.Status == status);
			return teams;
		}

		/// <summary>
		/// Возвращает команду по ID
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="id">ID команды</param>
		/// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы команд или нет такой команды</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/Teams/5
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/Teams/{id}")]
		public async Task<ActionResult<Team>> GetTeam(string password, long id)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			if (_context.Teams == null)
			{
				return NotFound();
			}
			var team = await _context.Teams.FindAsync(id);
			if (team == null)
			{
				return NotFound();
			}
			return team;
		}

		/// <summary>
		/// Обновляет данные команды
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="id">ID команды</param>
		/// <param name="team">Команда</param>
		/// <response code="204">ОК</response>
		/// <response code="404">В БД нет такой команды или капитана</response>
		/// <response code="400">Переданный ID и ID команды не совпадают</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// PUT: api/Administration/adminadmin/Teams/5
		[ProducesResponseType(204)]
		[ProducesResponseType(404)]
		[ProducesResponseType(400)]
		[ProducesResponseType(401)]
		[HttpPut("{password}/Teams/{id}")]
		public async Task<IActionResult> PutTeam(string password, long id, Team team)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			if (id != team.Id)
			{
				return BadRequest();
			}
			// Если заявка одобрена - даем статус капитана
			if (team.Status == 1)
			{
				var captain = await _context.Players.FindAsync(team.CaptainPhoneNumber);
				if (captain == null)
				{
					return NotFound();
				}
				captain.IsCaptain = true;
				_context.Entry(captain).State = EntityState.Modified;
			}
			_context.Entry(team).State = EntityState.Modified;
			try
			{
				await _context.SaveChangesAsync();
			}
			catch (DbUpdateConcurrencyException)
			{
				if (!TeamExists(id))
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
		/// Возвращает все переписки администратора
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <response code="200">ОК</response>
		/// <response code="404">Нет переписок с таким пользователем</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Adminisration/adminadmin/Chats
		[ProducesResponseType(200)]
		[ProducesResponseType(404)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/Chats")]
		public async Task<ActionResult<IEnumerable<Chat>>> GetChats(string password)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			var c = await _context.Chats.Include(c => c.LastMessage)
										.Where(c => c.PhoneNumber1 == "88888888888" || c.PhoneNumber2 == "88888888888")
										.ToListAsync();
			if (c == null)
				return NotFound();
			return c;
		}

		/// <summary>
		/// Возвращает 100 последних сообщений между конкретным пользователем и администратором
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="phone">Номер телефона пользователя</param>
		/// <param name="n">Какая по счету подгрузка</param>
		/// <response code="200">ОК</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// GET: api/Administration/adminadmin/Messages/89169307114/0
		[ProducesResponseType(200)]
		[ProducesResponseType(401)]
		[HttpGet("{password}/Messages/{phone}/{n}")]
		public async Task<ActionResult<IEnumerable<Message>>> GetMessages(string password, string phone, int n)
		{
			if (this.password != password)
			{
				return Unauthorized();
			}
			// Находим все сообщения между пользователем и администратором
			var messages = await _context.Messages.ToListAsync();
			messages = messages.FindAll(m => (m.Sender == phone && m.Receiver == "88888888888") || (m.Sender == "88888888888" && m.Receiver == phone));
			// Сортируем по времени отправки
			messages = messages.OrderByDescending(m => m.SendTime).ToList();
			// Пропускаем n*100 сообщений
			messages = messages.Skip(n * 100).ToList();
			// Берем 100 сообщений
			messages = messages.Take(100).ToList();
			return messages;
		}

		/// <summary>
		/// Добавление нового сообщения
		/// </summary>
		/// <param name="password">Логин-пароль администратора</param>
		/// <param name="message">Сообщение</param>
		/// <response code="201">ОК</response>
		/// <response code="400">Такое сообщение уже есть, или нет такой переписки</response>
		/// <response code="401">Неверный логин-пароль администратора</response>
		// POST: api/Administration/adminadmin/Messages
		[ProducesResponseType(201)]
		[ProducesResponseType(400)]
		[ProducesResponseType(401)]
		[HttpPost("{password}/Messages")]
        public async Task<ActionResult<Message>> PostMessage(string password, Message message)
        {
            if (this.password != password)
            {
                return Unauthorized();
            }
            // Если такое сообщение уже есть
            var m = await _context.Messages.FindAsync(message.Id);
            if (m != null)
                return BadRequest();
            // Находим переписку
            var chat = await _context.Chats.FirstOrDefaultAsync(c => (c.PhoneNumber1 == message.Sender && c.PhoneNumber2 == message.Receiver) ||
                                                                     (c.PhoneNumber2 == message.Sender && c.PhoneNumber1 == message.Receiver));
            if (chat == null)
                return BadRequest();
            // Обновляем данные переписки
            chat.LastMessage = message;
            _context.Entry(chat).State = EntityState.Modified;
            // Добавляем сообщение
            _context.Messages.Add(message);
            await _context.SaveChangesAsync();
            return CreatedAtAction("GetMessage", new { id = message.Id }, message);
        }

        /// <summary>
        /// Возвращает конкретное сообщение
        /// </summary>
        /// <param name="id">ID сообщения</param>
        /// <response code="200">ОК</response>
        /// <response code="404">В БД нет таблицы сообщений или нет такого сообщения</response>
        // GET: api/Administration/Messages/1
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet("Messages/{id}")]
        public async Task<ActionResult<Message>> GetMessage(long id)
        {
            if (_context.Messages == null)
            {
                return NotFound();
            }
            var m = await _context.Messages.FirstOrDefaultAsync(m => m.Id == id);
            if (m == null)
            {
                return NotFound();
            }
            return m;
        }

        /// <summary>
        /// Существует ли команда
        /// </summary>
        /// <param name="id">ID команды</param>
        private bool TeamExists(long id)
		{
			return (_context.Teams?.Any(e => e.Id == id)).GetValueOrDefault();
		}
	}
}
