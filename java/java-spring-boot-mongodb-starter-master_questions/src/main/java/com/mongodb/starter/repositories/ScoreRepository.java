package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Score;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository {

    Score save(Score score); //insert

    List<Score> findAll(); //find all scores
    
    List<Score> findTop20(); //find all scores
    
    List<Score> findAllUser(String nickname); // find all scores with nickname

    Score findOne(int score_id); // find 1 score

    long count(); // count all scores

    long delete(int score_id); //delete 1 score

    Score update(Score score); //update 1 score

}
