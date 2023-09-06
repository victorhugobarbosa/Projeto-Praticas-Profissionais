using Microsoft.EntityFrameworkCore;
using ApiProjeto.Model;
using System.Diagnostics.CodeAnalysis;

namespace ApiProjeto.Data
{
    public class GameContext : DbContext
    {
        protected readonly IConfiguration Configuration;

        public GameContext(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder options){
            // connect to sql server with connection string from app settings
            options.UseSqlServer(Configuration.GetConnectionString("StringConexaoSQLServer"));
        }

        public DbSet<Player>? Player {get; set;}
        public DbSet<Rodadas>? Rodadas {get; set;}
    }


}