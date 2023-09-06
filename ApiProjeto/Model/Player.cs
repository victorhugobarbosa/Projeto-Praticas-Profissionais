using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace ApiProjeto.Model
{
    public class Player
    {
        [Key]
        public string? nome {get; set;}
        public string? senha {get; set;}
        public string? apelido {get; set;}
        public int maiorRodada {get; set;}
    }
}