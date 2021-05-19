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
import com.mongodb.starter.models.User;

@Repository
public class MongoDBUserRepository implements UserRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<User> userCollection;

    @PostConstruct
    void init() {
        userCollection = client.getDatabase("trivia").getCollection("user", User.class);
    }

    @Override
    public User save(User user) {
        List<User> users = findAll();
        int max_id = 0, id;
        for (User usuario : users){
            id = usuario.getUser_id();
            if (id > max_id){
                max_id = id;
            }
        }
        user.setUser_id(max_id + 1);
        userCollection.insertOne(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userCollection.find().into(new ArrayList<>());
    }

    @Override
    public User findOne(int user_id) {
        return userCollection.find(eq("user_id", user_id)).first();
    }

    @Override
    public long count() {
        return userCollection.countDocuments();
    }

    @Override
    public long delete(int user_id) {
        return userCollection.deleteOne(eq("user_id", user_id)).getDeletedCount();
    }

    @Override
    public User update(User user) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return userCollection.findOneAndReplace(eq("user_id", user.getUser_id()), user, options);
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
