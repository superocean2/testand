using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace API.Models
{
    public class Income
    {
        public long id { get; set; }
        public long categoryid {get;set;}
        public double amount { get; set; }
        public String date { get; set; }
        public String hour { get; set; }
        public long userid { get; set; }
        public String description { get; set; }
    }
}