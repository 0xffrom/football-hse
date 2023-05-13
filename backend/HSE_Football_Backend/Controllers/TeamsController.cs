using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HSE_Football_Backend.Data;
using HSE_Football_Backend.Models;
using Microsoft.AspNetCore.Authorization;

namespace HSE_Football_Backend.Controllers
{
    /// <summary>
	/// Контроллер команд
	/// </summary>
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class TeamsController : ControllerBase
    {
        /// <summary>
		/// Контекст базы данных
		/// </summary>
        private readonly HSEFootballContext _context;

        /// <summary>
		/// Конструктор
		/// </summary>
		/// <param name="context">Контекст БД</param>
        public TeamsController(HSEFootballContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Возвращает все команды
        /// </summary>
        /// <response code="200">ОК</response>
        /// <response code="404">В БД нет таблицы команд</response>
        // GET: api/Teams
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Team>>> GetTeams()
        {
            if (_context.Teams == null)
            {
                return NotFound();
            }
            return await _context.Teams.ToListAsync();
        }

        /// <summary>
        /// Возвращает команду по ID
        /// </summary>
        /// <param name="id">ID команды</param>
        /// <response code="200">ОК</response>
        /// <response code="404">В БД нет таблицы команд или нет такой команды</response>
        // GET: api/Teams/5
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet("{id}")]
        public async Task<ActionResult<Team>> GetTeam(long id)
        {
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
        /// <param name="id">ID команды</param>
        /// <param name="team">Команда</param>
        /// <response code="204">ОК</response>
        /// <response code="404">В БД нет такой команды</response>
		/// <response code="400">Переданный ID и ID команды не совпадают</response>
        // PUT: api/Teams/5
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [ProducesResponseType(400)]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTeam(long id, Team team)
        {
            if (id != team.Id)
            {
                return BadRequest();
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
        /// Создает команду
        /// </summary>
        /// <param name="team">Команда</param>
        /// <response code="201">ОК</response>
        /// <response code="409">Уже есть команда с таким ID</response>
        /// <response code="500">>В БД нет таблицы команд</response>
        // POST: api/Teams
        [ProducesResponseType(201)]
        [ProducesResponseType(409)]
        [ProducesResponseType(500)]
        [HttpPost]
        public async Task<ActionResult<Team>> PostTeam(Team team)
        {
            if (_context.Teams == null)
            {
                return Problem("Entity set 'HSEFootballContext.Teams'  is null.");
            }
            _context.Teams.Add(team);
            await _context.SaveChangesAsync();
            return CreatedAtAction("GetTeam", new { id = team.Id }, team);
        }

        /// <summary>
        /// Удаляет команду
        /// </summary>
        /// <param name="id">ID команды</param>
        /// <response code="204">ОК</response>
        /// <response code="404">В БД нет таблицы команд или нет такой команды</response>
        // DELETE: api/Teams/5
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTeam(long id)
        {
            if (_context.Teams == null)
            {
                return NotFound();
            }
            var team = await _context.Teams.FindAsync(id);
            if (team == null)
            {
                return NotFound();
            }
            var captain = await _context.Players.FindAsync(team.CaptainPhoneNumber);
            captain.IsCaptain = false;
            _context.Teams.Remove(team);
            _context.Entry(captain).State = EntityState.Modified;
            await _context.SaveChangesAsync();
            return NoContent();
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
