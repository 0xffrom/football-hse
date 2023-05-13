using HSE_Football_Backend.Data;
using HSE_Football_Backend.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HSE_Football_Backend.Controllers
{
    /// <summary>
    /// Контроллер сообщений
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class MessagesController : ControllerBase
    {
        /// <summary>
        /// Контекст базы данных
        /// </summary>
        private readonly HSEFootballContext _context;

        /// <summary>
        /// Конструктор
        /// </summary>
        /// <param name="context">Контекст базы данных</param>
        public MessagesController(HSEFootballContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Возвращает конкретное сообщение
        /// </summary>
        /// <param name="id">ID сообщения</param>
        /// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы сообщений или нет такого сообщения</response>
        // GET: api/Messages/1
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet("{id}")]
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
        /// Возвращает все сообщения
        /// </summary>
        /// <response code="200">ОК</response>
		/// <response code="404">В БД нет таблицы сообщений</response>
        // GET: api/Messages
        [ProducesResponseType(200)]
        [ProducesResponseType(404)]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Message>>> GetMessages()
        {
            if (_context.Messages == null)
            {
                return NotFound();
            }
            return await _context.Messages.ToListAsync();
        }

        /// <summary>
        /// Возвращает 100 последних сообщений между двумя конкретными пользователями
        /// </summary>
        /// <param name="phone1">Номер телефона 1</param>
        /// <param name="phone2">Номер телефона 2</param>
        /// <param name="n">Какая по счету подгрузка</param>
        /// <response code="200">ОК</response>
        // GET: api/Messages/89169307114/89159297013/0
        [ProducesResponseType(200)]
        [HttpGet("{phone1}/{phone2}/{n}")]
        public async Task<ActionResult<IEnumerable<Message>>> GetMessages(string phone1, string phone2, int n)
        {
            // Находим все сообщения между этими двумя пользователями
            var messages = await _context.Messages.ToListAsync();
            messages = messages.FindAll(m => (m.Sender == phone1 && m.Receiver == phone2) || (m.Sender == phone2 && m.Receiver == phone1));
            // Сортируем по времени отправки
            messages = messages.OrderByDescending(m => m.SendTime).ToList();
            // Пропускаем n*100 сообщений
            messages = messages.Skip(n * 100).ToList();
            // Берем 100 сообщений
            messages = messages.Take(100).ToList();
            return messages;
        }

        /// <summary>
        /// Изменение конкретного сообщения
        /// </summary>
        /// <param name="id">ID сообщения</param>
        /// <param name="message">Сообщение</param>
        /// <response code="204">ОК</response>
		/// <response code="404">В БД нет такого сообщения</response>
		/// <response code="400">Переданный ID и ID сообщения не совпадают</response>
        // PUT: api/Messages/1
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [ProducesResponseType(400)]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutMessage(long id, Message message)
        {
            if (id != message.Id)
                return BadRequest();
            _context.Entry(message).State = EntityState.Modified;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MessageExists(id))
                    return NotFound();
                else
                    throw;
            }
            return NoContent();
        }

        /// <summary>
        /// Добавление нового сообщения
        /// </summary>
        /// <param name="message">Сообщение</param>
        /// <response code="201">ОК</response>
		/// <response code="400">Такое сообщение уже есть, или нет такой переписки</response>
        // POST: api/Messages
        [ProducesResponseType(201)]
        [ProducesResponseType(400)]
        [HttpPost]
        public async Task<ActionResult<Message>> PostMessage(Message message)
        {
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
        /// Удаление сообщения
        /// </summary>
        /// <param name="id">ID сообщения</param>
        /// <response code="204">ОК</response>
		/// <response code="404">В БД нет такого сообщения</response>
        // DELETE: api/Messages/5
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteMessage(long id)
        {
            var message = await _context.Messages.FindAsync(id);
            if (message == null)
                return NotFound();
            // Удаляем сообщение
            _context.Messages.Remove(message);
            await _context.SaveChangesAsync();
            return NoContent();
        }

        /// <summary>
        /// Проверка наличия сообщения
        /// </summary>
        /// <param name="id">ID сообщения</param>
        private bool MessageExists(long id)
        {
            return _context.Messages.Any(e => e.Id == id);
        }
    }
}
