using HSE_Football_Backend.Data;
using HSE_Football_Backend.Auth;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Reflection;
using HSE_Football_Backend.Other;

var builder = WebApplication.CreateBuilder(args);

// ����������� CORS
builder.Services.AddCors(options =>
{
    options.AddPolicy("all", builder =>
    {
        builder.AllowAnyOrigin()
        .AllowAnyMethod()
        .AllowAnyHeader();
    });
});

// ����������� SignalR ��� ����
builder.Services.AddSignalR();

// ����������� ������������
builder.Services.AddControllers();

// ����������� Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    // ��������� XML ������������
    var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
    var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
    c.IncludeXmlComments(xmlPath);
});

// ����������� ��������� ���� ������
if (Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT") == "Production")
    builder.Services.AddDbContext<HSEFootballContext>(options =>
        options.UseSqlServer(builder.Configuration.GetConnectionString("HSEFootballContextProd")));
else
    builder.Services.AddDbContext<HSEFootballContext>(options =>
        options.UseSqlServer(builder.Configuration.GetConnectionString("HSEFootballContext")));
builder.Services.BuildServiceProvider().GetService<HSEFootballContext>().Database.Migrate();

// ����������� ��������������
builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
        .AddJwtBearer(options =>
        {
            options.RequireHttpsMetadata = false;
            options.TokenValidationParameters = new TokenValidationParameters
            {
                // ��������, ����� �� �������������� �������� ��� ��������� ������
                ValidateIssuer = true,
                // ������, �������������� ��������
                ValidIssuer = AuthOptions.ISSUER,

                // ����� �� �������������� ����������� ������
                ValidateAudience = true,
                // ��������� ����������� ������
                ValidAudience = AuthOptions.AUDIENCE,
                // ����� �� �������������� ����� �������������
                ValidateLifetime = true,

                // ��������� ����� ������������
                IssuerSigningKey = AuthOptions.GetSymmetricSecurityKey(),
                // ��������� ����� ������������
                ValidateIssuerSigningKey = true,
                // �������� ���������� �� ������� ������� ����� ������
                ClockSkew = TimeSpan.Zero
            };
        });
builder.Services.AddSingleton<IJWTManager, JWTManager>();

var app = builder.Build();

app.UseCors("all");

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.UseRouting();

app.UseStaticFiles();

app.UseAuthentication();
app.UseAuthorization();

app.UseEndpoints(endpoints =>
{
    endpoints.MapHub<ChatHub>("/chat");
    endpoints.MapControllers();
});

app.Run();
