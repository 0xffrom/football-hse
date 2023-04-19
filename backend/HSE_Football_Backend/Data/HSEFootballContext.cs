using HSE_Football_Backend.Models;
using Microsoft.EntityFrameworkCore;

namespace HSE_Football_Backend.Data
{
    /// <summary>
    /// Контекст базы данных
    /// </summary>
    public class HSEFootballContext : DbContext
    {
        /// <summary>
        /// Конструктор
        /// </summary>
        /// <param name="options">Параметры базы данных</param>
        public HSEFootballContext(DbContextOptions<HSEFootballContext> options) : base(options)
        {
            Database.EnsureCreated();
        }

        /// <summary>
        /// Создание моделей
        /// </summary>
        /// <param name="builder">"Строитель", связывающий модеди с контекстом БД</param>
        protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);
        }

        /// <summary>
        /// Игроки
        /// </summary>
        public DbSet<Player> Players { get; set; }

        /// <summary>
        /// Команды
        /// </summary>
        public DbSet<Team> Teams { get; set; }

        /// <summary>
        /// Заявки игроков
        /// </summary>
        public DbSet<PlayerApplication> PlayerApplications { get; set; }

        /// <summary>
        /// Заявки команд
        /// </summary>
        public DbSet<TeamApplication> TeamApplications { get; set; }

        /// <summary>
        /// Сообщения
        /// </summary>
        public DbSet<Message> Messages { get; set; }

        /// <summary>
        /// Переписки
        /// </summary>
        public DbSet<Chat> Chats { get; set; }
    }
}
