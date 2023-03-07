using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HSE_Football_Backend.Data;
using HSE_Football_Backend.Models;

namespace HSE_Football_Backend.Controllers
{
    /// <summary>
	/// Контроллер заявок команд
	/// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class TeamApplicationsController : ControllerBase
    {
        /// <summary>
		/// Контекст базы данных
		/// </summary>
        private readonly HSEFootballContext _context;

        /// <summary>
		/// Конструктор
		/// </summary>
		/// <param name="context">Контекст БД</param>
        public TeamApplicationsController(HSEFootballContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Возвращает все заявки команд
        /// </summary>
        /// <response code="200">ОК</response>
        /// <response code="404">В БД нет таблицы заявок команд</response>
        // GET: api/TeamApplications
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TeamApplication>>> GetTeamApplications()
        {
            if (_context.TeamApplications == null)
            {
                return NotFound();
            }
            return await _context.TeamApplications.ToListAsync();
        }

        /// <summary>
        /// Возвращает заявку команды по ID
        /// </summary>
        /// <param name="id">ID заявки команды</param>
        /// <response code="200">ОК</response>
        /// <response code="404">В БД нет таблицы заявок команд или нет такой заявки команды</response>
        // GET: api/TeamApplications/5
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet("{id}")]
        public async Task<ActionResult<TeamApplication>> GetTeamApplication(long id)
        {
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
        /// Обновляет данные заявки команды
        /// </summary>
        /// <param name="id">ID заявки команды</param>
        /// <param name="teamApplication">Заявка команды</param>
        /// <response code="204">ОК</response>
        /// <response code="404">В БД нет такой заявки команды</response>
		/// <response code="400">Переданный ID и ID заявки команды не совпадают</response>
        // PUT: api/TeamApplications/5
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [ProducesResponseType(400)]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTeamApplication(long id, TeamApplication teamApplication)
        {
            if (id != teamApplication.Id)
            {
                return BadRequest();
            }
            _context.Entry(teamApplication).State = EntityState.Modified;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TeamApplicationExists(id))
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
        /// Создает заявку команды
        /// </summary>
        /// <param name="teamApplication">Заявка команды</param>
        /// <response code="201">ОК</response>
        /// <response code="409">Уже есть заявка команды с таким ID</response>
        /// <response code="500">>В БД нет таблицы заявок команд</response>
        // POST: api/TeamApplications
        [ProducesResponseType(201)]
        [ProducesResponseType(409)]
        [ProducesResponseType(500)]
        [HttpPost]
        public async Task<ActionResult<TeamApplication>> PostTeamApplication(TeamApplication teamApplication)
        {
            if (_context.TeamApplications == null)
            {
                return Problem("Entity set 'HSEFootballContext.TeamApplications'  is null.");
            }
            _context.TeamApplications.Add(teamApplication);
            await _context.SaveChangesAsync();
            return CreatedAtAction("GetTeamApplication", new { id = teamApplication.Id }, teamApplication);
        }

        /// <summary>
        /// Удаляет заявку команды
        /// </summary>
        /// <param name="id">ID заявки команды</param>
        /// <response code="204">ОК</response>
        /// <response code="404">В БД нет таблицы заявок команд или нет такой заявки команды</response>
        // DELETE: api/TeamApplications/5
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTeamApplication(long id)
        {
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
        /// <param name="position">Позиция</param>
        /// <param name="tournaments">Турниры</param>
        /// <response code="200">ОК</response>
        // GET: api/TeamApplications/filters?position=1&tournaments=1
        [ProducesResponseType(200)]
        [HttpGet("filters")]
        public async Task<ActionResult<IEnumerable<TeamApplication>>> GetFilteredTeamApplications(int position = -1, int tournaments = -1)
        {
            var applications = await _context.TeamApplications.ToListAsync();
            if (position != -1)
                applications = applications.FindAll(a => (a.PlayerPosition & position) > 0);
            if (tournaments != -1)
                applications = applications.FindAll(a => (a.Tournaments & tournaments) > 0);
            return applications;
        }

        /// <summary>
		/// Существует ли заявка команды
		/// </summary>
		/// <param name="id">ID заявки команды</param>
        private bool TeamApplicationExists(long id)
        {
            return (_context.TeamApplications?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
