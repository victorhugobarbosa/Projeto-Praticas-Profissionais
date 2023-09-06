using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace ApiProjeto.Model
{
    public class Rodadas
    {
        [Key]
        public int numeroRodadas {get; set;}
        public int qtaInimigos {get; set;}
    }
}