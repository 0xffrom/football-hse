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
    public class PlayerApplicationsController : ControllerBase
    {
        private readonly HSEFootballContext _context;

        public PlayerApplicationsController(HSEFootballContext context)
        {
            _context = context;
        }

        // GET: api/PlayerApplications
        [HttpGet]
        public async Task<ActionResult<IEnumerable<PlayerApplication>>> GetPlayerApplications()
        {
          if (_context.PlayerApplications == null)
          {
              return NotFound();
          }
            return await _context.PlayerApplications.ToListAsync();
        }

        // GET: api/PlayerApplications/5
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

        // PUT: api/PlayerApplications/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
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

        // POST: api/PlayerApplications
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<PlayerApplication>> PostPlayerApplication(PlayerApplication playerApplication)
        {
          if (_context.PlayerApplications == null)
          {
              return Problem("Entity set 'HSEFootballContext.PlayerApplications'  is null.");
          }
            _context.PlayerApplications.Add(playerApplication);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPlayerApplication", new { id = playerApplication.Id }, playerApplication);
        }

        // DELETE: api/PlayerApplications/5
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

        private bool PlayerApplicationExists(long id)
        {
            return (_context.PlayerApplications?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
