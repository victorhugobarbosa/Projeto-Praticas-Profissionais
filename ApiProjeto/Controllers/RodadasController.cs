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
    public class RodadasController : ControllerBase
    {
        private GameContext _context;
        public RodadasController(GameContext context){
            // constructor
            _context = context; 
        }

        [HttpGet]
        public ActionResult<List<Rodadas>> GetAll(){
            return _context.Rodadas.ToList();
        }

        [HttpGet("NumeroRodada")]
        public ActionResult<Rodadas> Get(int NumeroRodada){
            try{
                var res = _context.Rodadas.Find(NumeroRodada);
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

        [HttpPut("{NumeroRodada}")]
        public async Task<IActionResult> put(int NumeroRodada, Rodadas dadosNewRodadas){
            try{
                var res = await _context.Rodadas.FindAsync(NumeroRodada);
                if(NumeroRodada != res.numeroRodadas) return BadRequest();

                res.numeroRodadas = dadosNewRodadas.numeroRodadas;
                res.qtaInimigos = dadosNewRodadas.qtaInimigos;
                await _context.SaveChangesAsync();
                return Created($"/api/Player/{dadosNewRodadas.numeroRodadas}",dadosNewRodadas);
            }
            catch{
                return this.StatusCode(
                    StatusCodes.Status500InternalServerError,
                    "Falha no acesso ao banco de dados."
                );
            }
        }

        [HttpPost]
        public async Task<ActionResult> post(Rodadas model){
            try{
                _context.Rodadas.Add(model);
                if(await _context.SaveChangesAsync() == 1){
                    return Created($"/api/Player/{model.numeroRodadas}", model);
                }
            }
            catch{
                return this.StatusCode(
                    StatusCodes.Status500InternalServerError,
                    "Falha no acesso ao banco de dados."
                );
            }
            return BadRequest();
        }     
    }
}