using HSE_Football_Backend.Data;
using HSE_Football_Backend.Auth;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Reflection;

var builder = WebApplication.CreateBuilder(args);

// Подключение контроллеров
builder.Services.AddControllers();

// Подключение Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    // Включение XML комментариев
    var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
    var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
    c.IncludeXmlComments(xmlPath);
});

// Подключение контекста базы данных
if (Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT") == "Production")
    builder.Services.AddDbContext<HSEFootballContext>(options =>
        options.UseSqlServer(builder.Configuration.GetConnectionString("HSEFootballContextProd")));
else
    builder.Services.AddDbContext<HSEFootballContext>(options =>
        options.UseSqlServer(builder.Configuration.GetConnectionString("HSEFootballContext")));
builder.Services.BuildServiceProvider().GetService<HSEFootballContext>().Database.Migrate();

// Подключение аутентификации
builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
        .AddJwtBearer(options =>
        {
            options.RequireHttpsMetadata = false;
            options.TokenValidationParameters = new TokenValidationParameters
            {
                // Укзывает, будет ли валидироваться издатель при валидации токена
                ValidateIssuer = true,
                // Строка, представляющая издателя
                ValidIssuer = AuthOptions.ISSUER,

                // Будет ли валидироваться потребитель токена
                ValidateAudience = true,
                // Установка потребителя токена
                ValidAudience = AuthOptions.AUDIENCE,
                // Будет ли валидироваться время существования
                ValidateLifetime = true,

                // Установка ключа безопасности
                IssuerSigningKey = AuthOptions.GetSymmetricSecurityKey(),
                // Валидация ключа безопасности
                ValidateIssuerSigningKey = true,
                // Параметр отвечающий за подсчет времени жизни токена
                ClockSkew = TimeSpan.Zero
            };
        });
builder.Services.AddSingleton<IJWTManager, JWTManager>();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

app.Run();
