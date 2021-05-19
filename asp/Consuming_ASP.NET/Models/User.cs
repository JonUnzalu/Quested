using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Consuming_ASP.NET.Models
{

    public class User
    {
        public string id { get; set; }
        public string nickname { get; set; }
        public string password { get; set; }
        public string email { get; set; }
        public string name { get; set;  }
        public string surname { get; set; }
        public string role { get; set; }
        public string highscore { get; set; }

        public User(string nickname, string password, string email, string name, string surname, string role, string highscore)
        {
            this.nickname = nickname;
            this.password = password;
            this.email = email;
            this.name = name;
            this.surname = surname;
            this.role = role;
            this.highscore = highscore;
        }

        public static bool login(string username, string password)
        {
            //var credential = MongoCredential.CreateMongoCRCredential("test", "user1", "password1");
            return true;
        }
    }
}