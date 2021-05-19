using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Consuming_ASP.NET.Models
{
    public class HighScore
    {
        public int Score_Id { get; set; }
        public int Score { get; set; }
        public string Nickname { get; set; }

        public HighScore(string nickname, int score)
        {
            this.Nickname = nickname;
            this.Score = score;
        }
    }
}