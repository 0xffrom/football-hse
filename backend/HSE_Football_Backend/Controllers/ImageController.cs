using HSE_Football_Backend.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HSE_Football_Backend.Controllers
{
    /// <summary>
    /// Контроллер загрузки фотографий
    /// </summary>
	[Route("api/[controller]")]
	[ApiController]
	public class ImageController : ControllerBase
	{
        /// <summary>
        /// Окружение
        /// </summary>
		public static IWebHostEnvironment? _environment;

        /// <summary>
        /// Контекст базы данных
        /// </summary>
        private readonly HSEFootballContext _context;

        /// <summary>
        /// Конструктор
        /// </summary>
        /// <param name="environment">Окружение</param>
        /// <param name="context">Контекст БД</param>
        public ImageController(IWebHostEnvironment environment, HSEFootballContext context)
		{
			_environment = environment;
            _context = context;
        }

        /// <summary>
        /// Загрузка фотографии игрока
        /// </summary>
        /// <param name="phone">Номер телефона игрока</param>
        /// <param name="image">Фотография</param>
        /// <response code="200">ОК</response>
        /// <response code="404">В БД нет такого игрока</response>
        // POST: api/Image/Player/89169307114
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpPost("Player/{phone}")]
		public async Task<IActionResult> PostPlayerPhoto(string phone, IFormFile image)
		{
			var player = await _context.Players.FindAsync(phone);
            if (player == null)
            {
                return NotFound();
            }
            if (image.Length > 0)
			{
				if (!Directory.Exists(_environment.WebRootPath + "Images"))
				{
					Directory.CreateDirectory(_environment.WebRootPath + "Images/");
				}
				using (FileStream filestream = System.IO.File.Create(_environment.WebRootPath + "Images/" + image.FileName))
				{
					image.CopyTo(filestream);
					filestream.Flush();
				}
			}
			player.Photo = "http://hse-football.ru/Images/" + image.FileName;
            _context.Entry(player).State = EntityState.Modified;
            await _context.SaveChangesAsync();
            return Ok();
		}

        /// <summary>
        /// Загрузка фотографии команды
        /// </summary>
        /// <param name="id">ID команды</param>
        /// <param name="image">Фотография</param>
        /// <response code="200">ОК</response>
        /// <response code="404">В БД нет такой команды</response>
        // POST: api/Image/Team/1
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpPost("Team/{id}")]
        public async Task<IActionResult> PostTeamLogo(long id, IFormFile image)
        {
            var team = await _context.Teams.FindAsync(id);
            if (team == null)
            {
                return NotFound();
            }
            if (image.Length > 0)
            {
                if (!Directory.Exists(_environment.WebRootPath + "Images"))
                {
                    Directory.CreateDirectory(_environment.WebRootPath + "Images/");
                }
                using (FileStream filestream = System.IO.File.Create(_environment.WebRootPath + "Images/" + image.FileName))
                {
                    image.CopyTo(filestream);
                    filestream.Flush();
                }
            }
            team.Logo = "http://hse-football.ru/Images/" + image.FileName;
            _context.Entry(team).State = EntityState.Modified;
            await _context.SaveChangesAsync();
            return Ok();
        }
    }
}
