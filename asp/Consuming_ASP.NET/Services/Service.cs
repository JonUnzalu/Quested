using Consuming_ASP.NET.Models;
using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;

namespace Consuming_ASP.NET.Services
{
    public class Service
    {
        private readonly IMongoCollection<Questions> _supplement;

        public Service(IConfiguration config)
        {
            var client = new MongoClient(config.GetConnectionString("trivia"));
            var database = client.GetDatabase("trivia");
            _supplement = database.GetCollection<Questions>("questions");
        }
        public async Task<List<Questions>> Get()
        {
            return await _supplement.Find(s => true).ToListAsync();
        }
        public async Task<Questions> Get(int id)
        {
            //Get a single supplement. 
            return await _supplement.Find(s => s.question_id == id).FirstOrDefaultAsync();
        }

        public async Task<Questions> Create(Questions s)
        {
            //Create a supplement.
            await _supplement.InsertOneAsync(s);
            return s;
        }
        public async Task<Questions> Update(int id, Questions s)
        {
            // Updates and existing supplement. 
            await _supplement.ReplaceOneAsync(su => su.question_id == id, s);
            return s;
        }

        public async Task Remove(int id)
        {
            //Removes a supplement.
            await _supplement.DeleteOneAsync(su => su.question_id == id);
        }

    }
}