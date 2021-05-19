package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository {

    Question save(Question question); // insert

    List<Question> findAll(); //find all questions

    Question findOne(int question_id); // find 1 question

    long count(); // count all questions
    
    long countCategories(); // count all categories

    long delete(int question_id); //delete 1 question

    Question update(Question question); //update 1 question
    
    List<Question> findAllType(String type);
    
    List<Question> findAllCategory(String category); // find question by categories

    List<Question> findAllDifficulty(String difficulty);
    
    List<Question> findUnavailable();
    
    List<Question> findAvailable();
}
