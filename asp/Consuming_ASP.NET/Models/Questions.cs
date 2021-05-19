using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Consuming_ASP.NET.Models
{
    public class Questions
    {
        public int question_id { get; set; }
        public string category { get; set; }
        public string type { get; set; }
        public string difficulty { get; set; }
        public string question { get; set; }
        public string correct_answer { get; set; }
        public string[] incorrect_answers { get; set; }
        public bool available { get; set; }
        public int act_score { get; set; }

        public static implicit operator Questions(List<Questions> v)
        {
            throw new NotImplementedException();
        }

        public Questions(string category, string type, string difficulty, string question, string correct_answer, string[] incorrect_answers, bool available)
        {
            this.category = category;
            this.type = type;
            this.difficulty = difficulty;
            this.question = question;
            this.correct_answer = correct_answer;
            this.incorrect_answers = incorrect_answers;
            this.available = available;
        }

        public Questions()
        {

        }
    }
}