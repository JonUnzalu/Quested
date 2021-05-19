package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Score;
import com.mongodb.starter.repositories.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class ScoreController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ScoreController.class);
    private final ScoreRepository scoreRepository;

    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @PostMapping("score")
    @ResponseStatus(HttpStatus.CREATED)
    public Score postScore(@RequestBody Score score) {
        return scoreRepository.save(score);
    }

    @GetMapping("scores")
    public List<Score> getScores() {
        return scoreRepository.findAll();
    }

    @GetMapping("scores/top")
    public List<Score> getTopScores() {
        return scoreRepository.findTop20();
    }

    @GetMapping("scores/nickname")
    public List<Score> getScoresNickname(@RequestParam(value = "nickname") String nickname) {
        return scoreRepository.findAllUser(nickname);
    }

    @GetMapping("score/{score_id}")
    public ResponseEntity<Score> getScore(int score_id) {
        Score score = scoreRepository.findOne(score_id);
        if (score == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(score);
    }

    @GetMapping("scores/count")
    public Long getCount() {
        return scoreRepository.count();
    }

    @DeleteMapping("score/{score_id}")
    public Long deleteScore(@PathVariable int score_id) {
        return scoreRepository.delete(score_id);
    }

    @PutMapping("score")
    public Score putScore(@RequestBody Score score) {
        return scoreRepository.update(score);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
