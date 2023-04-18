using HSE_Football_Backend.Data;
using HSE_Football_Backend.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HSE_Football_Backend.Controllers
{
    /// <summary>
    /// Контроллер переписок
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    public class ChatsController : ControllerBase
    {
        /// <summary>
        /// Контекст базы данных
        /// </summary>
        private readonly HSEFootballContext _context;

        /// <summary>
        /// Конструктор
        /// </summary>
        /// <param name="context">Контекст базы данных</param>
        public ChatsController(HSEFootballContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Возвращает конкретную переписку
        /// </summary>
        /// <param name="id">ID переписки</param>
        /// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы переписок или нет такой переписки</response>
        // GET: api/Chats/1
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet("{id}")]
        public async Task<ActionResult<Chat>> GetChat(long id)
        {
            if (_context.Chats == null)
            {
                return NotFound();
            }
            var chat = await _context.Chats.Include(c => c.LastMessage).FirstOrDefaultAsync(c => c.Id == id);
            if (chat == null)
            {
                return NotFound();
            }
            return chat;
        }

        /// <summary>
        /// Возвращает все переписки
        /// </summary>
        /// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы чатов</response>
        // GET: api/Chats
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Chat>>> GetChats()
        {
            if (_context.Chats == null)
            {
                return NotFound();
            }
            return await _context.Chats.Include(c => c.LastMessage).ToListAsync();
        }

        /// <summary>
        /// Возвращает все переписки конкретного пользователя
        /// </summary>
        /// <param name="phoneNumber">Номер телефона</param>
        /// <response code="200">ОК</response>
		/// <response code="404">Нет переписок с таким пользователем</response>
        // GET: api/Chats/user/89169307114
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet("user/{phoneNumber}")]
        public async Task<ActionResult<IEnumerable<Chat>>> GetChats(string phoneNumber)
        {
            var c = await _context.Chats.Include(c => c.LastMessage)
                                        .Where(c => c.PhoneNumber1 == phoneNumber || c.PhoneNumber2 == phoneNumber)
                                        .ToListAsync();
            if (c == null)
                return NotFound();
            return c;
        }

        /// <summary>
        /// Изменение конкретной переписки
        /// </summary>
        /// <param name="id">ID переписки</param>
        /// <param name="chat">Переписка</param>
        /// <response code="204">ОК</response>
		/// <response code="404">В БД нет такого чата</response>
		/// <response code="400">Переданный ID и ID переписки не совпадают</response>
        // PUT: api/Chats/1
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [ProducesResponseType(400)]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutChat(long id, Chat chat)
        {
            if (id != chat.Id)
                return BadRequest();
            _context.Entry(chat).State = EntityState.Modified;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ChatExists(id))
                    return NotFound();
                else
                    throw;
            }
            return NoContent();
        }

        /// <summary>
        /// Добавление новой переписки
        /// </summary>
        /// <param name="chat">Переписка</param>
        /// <response code="201">ОК</response>
		/// <response code="400">Такая переписка уже есть, или нет таких собеседников</response>
        // POST: api/Chats
        [ProducesResponseType(201)]
        [ProducesResponseType(400)]
        [HttpPost]
        public async Task<ActionResult<Chat>> PostChat(Chat chat)
        {
            // Если такая переписка уже есть
            var c = await _context.Chats.FindAsync(chat.Id);
            if (c != null)
                return BadRequest();
            // Находим собеседников
            var user1 = await _context.Players.FindAsync(chat.PhoneNumber1);
            var user2 = await _context.Players.FindAsync(chat.PhoneNumber2);
            if (user1 == null || user2 == null || user1 == user2)
                return BadRequest();
            // Добавляем переписку
            _context.Chats.Add(chat);
            // Сохраняем изменения
            await _context.SaveChangesAsync();
            return CreatedAtAction("GetChat", new { id = chat.Id }, chat);
        }

        /// <summary>
        /// Удаление переписки
        /// </summary>
        /// <param name="id">ID переписки</param>
        /// <response code="204">ОК</response>
		/// <response code="404">В БД нет такой переписки</response>
        // DELETE: api/Chats/5
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteChat(long id)
        {
            var chat = await _context.Chats.FindAsync(id);
            if (chat == null)
                return NotFound();
            // Удаляем переписку
            _context.Chats.Remove(chat);
            await _context.SaveChangesAsync();
            return NoContent();
        }

        /// <summary>
        /// Проверка наличия переписки
        /// </summary>
        /// <param name="id">ID переписки</param>
        private bool ChatExists(long id)
        {
            return _context.Chats.Any(e => e.Id == id);
        }
    }
}
