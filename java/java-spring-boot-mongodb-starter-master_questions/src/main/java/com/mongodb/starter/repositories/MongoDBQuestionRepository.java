package com.mongodb.starter.repositories;

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
import com.mongodb.starter.models.Question;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class MongoDBQuestionRepository implements QuestionRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<Question> questionCollection;

    @PostConstruct
    void init() {
        questionCollection = client.getDatabase("trivia").getCollection("questions", Question.class);
    }

    @Override
    public Question save(Question question) {
        List<Question> questions = findAll();
        int max_id = 0, id;
        for (Question quest : questions){
            id = quest.getQuestion_id();
            if (id > max_id){
                max_id = id;
            }
        }
        question.setAvailable(false);
        question.setQuestion_id(max_id + 1);
        if(question.getType().equals("boolean")){
            if(question.getIncorrect_answers().size() == 1
                    ){
                questionCollection.insertOne(question);
                return question;                
            }
        } else if (question.getType().equals("multiple")) {
            if(question.getIncorrect_answers().size() == 3){
                questionCollection.insertOne(question);
                return question;                
            }
        }
        return null;
    }

    @Override
    public List<Question> findAll() {
        return questionCollection.find().into(new ArrayList<>());
    }
    
    @Override
    public List<Question> findAllCategory(@RequestParam(value="category") String category) {
        return questionCollection.find(in("category", category)).into(new ArrayList<>());
    }
    
    @Override
    public List<Question> findAllType(@RequestParam(value="type") String type) {
        return questionCollection.find(in("type", type)).into(new ArrayList<>());
    }
    
    @Override
    public List<Question> findAllDifficulty(@RequestParam(value="difficulty") String difficulty) {
        return questionCollection.find(in("difficulty", difficulty)).into(new ArrayList<>());
    }
    
    @Override
    public List<Question> findUnavailable() {
        return questionCollection.find(in("available", false)).into(new ArrayList<>());
    }
    
    @Override
    public List<Question> findAvailable() {
        return questionCollection.find(in("available", true)).into(new ArrayList<>());
    }
    
    @Override
    public Question findOne(int question_id) {
        return questionCollection.find(eq("question_id", question_id)).first();
    }

    @Override
    public long count() {
        return questionCollection.countDocuments();
    }
    
    @Override
    public long countCategories() {
        long n = 0;
        for (String s : questionCollection.distinct("category", String.class)) {
            n++;
        }
        return n;
    }

    @Override
    public long delete(int question_id) {
        return questionCollection.deleteOne(eq("question_id", question_id)).getDeletedCount();
    }

    @Override
    public Question update(Question question) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return questionCollection.findOneAndReplace(eq("question_id", question.getQuestion_id()), question, options);
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }

   
}
