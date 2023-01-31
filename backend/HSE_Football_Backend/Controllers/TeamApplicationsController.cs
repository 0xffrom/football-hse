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
    [Route("api/[controller]")]
    [ApiController]
    public class TeamApplicationsController : ControllerBase
    {
        private readonly HSEFootballContext _context;

        public TeamApplicationsController(HSEFootballContext context)
        {
            _context = context;
        }

        // GET: api/TeamApplications
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TeamApplication>>> GetTeamApplications()
        {
          if (_context.TeamApplications == null)
          {
              return NotFound();
          }
            return await _context.TeamApplications.ToListAsync();
        }

        // GET: api/TeamApplications/5
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

        // PUT: api/TeamApplications/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
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

        // POST: api/TeamApplications
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
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

        // DELETE: api/TeamApplications/5
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

        private bool TeamApplicationExists(long id)
        {
            return (_context.TeamApplications?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
