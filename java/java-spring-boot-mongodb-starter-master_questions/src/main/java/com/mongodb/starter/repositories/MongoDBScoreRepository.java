package com.mongodb.starter.repositories;

import com.mongodb.BasicDBObject;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;
import com.mongodb.starter.models.Score;
import com.mongodb.starter.models.User;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class MongoDBScoreRepository implements ScoreRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<Score> scoreCollection;

    @PostConstruct
    void init() {
        scoreCollection = client.getDatabase("trivia").getCollection("score", Score.class);
    }

    @Override
    public Score save(Score score) {
        List<Score> scores = findAll();
        int max_id = 0, id;
        for (Score sc : scores){
            id = sc.getScore_id();
            if (id > max_id){
                max_id = id;
            }
        }
        score.setScore_id(max_id + 1);
        scoreCollection.insertOne(score);
        return score;
    }

    @Override
    public List<Score> findAll() {
        return scoreCollection.find().into(new ArrayList<>());
    }
    
    @Override
    public List<Score> findAllUser(@RequestParam(value="nickname") String nickname) {
        return scoreCollection.find(in("nickname", nickname)).into(new ArrayList<>());
    }

    @Override
    public Score findOne(int score_id) {
        return scoreCollection.find(eq("score_id", score_id)).first();
    }

    @Override
    public long count() {
        return scoreCollection.countDocuments();
    }
    
    @Override
    public long delete(int score_id) {
        return scoreCollection.deleteOne(eq("score_id", score_id)).getDeletedCount();
    }

    @Override
    public Score update(Score score) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return scoreCollection.findOneAndReplace(eq("score_id", score.getScore_id()), score, options);
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }

    @Override
    public List<Score> findTop20() {
        return scoreCollection.find().sort(new BasicDBObject("score", -1)).limit(20).into(new ArrayList<>());
    }

   
}
