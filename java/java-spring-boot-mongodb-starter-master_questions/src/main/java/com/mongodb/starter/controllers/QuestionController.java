package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Question;
import com.mongodb.starter.repositories.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final static Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("question")
    @ResponseStatus(HttpStatus.CREATED)
    public Question postQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    @GetMapping("questions")
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }
    
    @GetMapping("questions/category")
    public List<Question> getQuestionsCategory(@RequestParam(value="category") String category) {
        return questionRepository.findAllCategory(category);
    }
    
    @GetMapping("questions/type")
    public List<Question> getQuestionType(@RequestParam(value="type") String type) {
        return questionRepository.findAllType(type);
    }
    
    @GetMapping("questions/difficulty")
    public List<Question> getQuestionDifficulty(@RequestParam(value="difficulty") String difficulty) {
        return questionRepository.findAllDifficulty(difficulty);
    }
    
    @GetMapping("questions/unavailable")
    public List<Question> getQuestionUnavailable() {
        return questionRepository.findUnavailable();
    }
    
    @GetMapping("questions/available")
    public List<Question> getQuestionAvailable() {
        return questionRepository.findAvailable();
    }

    @GetMapping("question/{question_id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int question_id) {
        Question question = questionRepository.findOne(question_id);
        if (question == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(question);
    }

    @GetMapping("questions/count")
    public Long getCount() {
        return questionRepository.count();
    }
    
    @GetMapping("questions/countCategories")
    public Long getCountCategories() {
        return questionRepository.countCategories();
    }

    @DeleteMapping("question/{question_id}")
    public Long deleteQuestion(@PathVariable int question_id) {
        return questionRepository.delete(question_id);
    }

    @PutMapping("question")
    public Question putQuestion(@RequestBody Question question) {
        return questionRepository.update(question);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
