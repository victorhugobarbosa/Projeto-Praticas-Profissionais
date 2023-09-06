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
    }
}