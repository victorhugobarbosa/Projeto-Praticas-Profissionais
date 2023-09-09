using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ApiProjeto.Data;
using ApiProjeto.Model;

namespace ApiProjeto.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PlayerController : ControllerBase
    {
        private GameContext _context;
        public PlayerController(GameContext context){
            // constructor
            _context = context; 
        }

        [HttpGet]
        public ActionResult<List<Player>> GetAll(){
            return _context.Player.ToList();
        }

        [HttpGet("NomePlayer")]
        public ActionResult<Player> Get(string NomePlayer){
            try{
                var res = _context.Player.Find(NomePlayer);
                if(res == null) return NotFound();
                return Ok(res);
            }
            catch{
                return this.StatusCode(
                    StatusCodes.Status500InternalServerError,
                    "Falha no acesso ao banco de dados."
                );
            }
        }

        [HttpPost]
        public async Task<ActionResult> post(Player model){
            try{
                _context.Player.Add(model);
                if(await _context.SaveChangesAsync() == 1){
                    return Created($"/api/Player/{model.apelido}", model);
                }
            }
            catch{
                return this.StatusCode(
                    StatusCodes.Status500InternalServerError,
                    "Falha no acesso ao banco de dados"
                );
            }
            return BadRequest();
        }

        [HttpDelete("{NomePlayer}")]
        public async Task<ActionResult> delete(string NomePlayer){
            try{
                var player = await _context.Player.FindAsync(NomePlayer);
                if(player == null) return NotFound();
                _context.Remove(player);
                await _context.SaveChangesAsync();
                return NoContent();
            }
            catch{
                return this.StatusCode(
                    StatusCodes.Status500InternalServerError,
                    "Falha no acesso ao banco de dados"
                );
            }
        }      
    }
}