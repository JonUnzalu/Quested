/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author nichei.anatoli
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {

    @JsonSerialize(using = ToStringSerializer.class)
    private int question_id;
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;
    private boolean available;

    public int getQuestion_id() {
        return question_id;
    }

    public Question setQuestion_id(int question_id) {
        this.question_id = question_id;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Question setCategory(String category) {
        this.category = category;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getType() {
        return type;
    }

    public Question setType(String type) {
        this.type = type;
        return this;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Question setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public Question setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public Question setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
        return this;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public Question setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
        return this;
    }

    @Override
    public String toString() {
        return "Question{" + "question_id=" + question_id + ", category=" + category + ", type=" + type + ", difficulty=" + difficulty + ", question=" + question + ", correct_answer=" + correct_answer + ", incorrect_answers=" + incorrect_answers + ", available=" + available + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        if (this.question_id != other.question_id) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.difficulty, other.difficulty)) {
            return false;
        }
        if (!Objects.equals(this.question, other.question)) {
            return false;
        }
        if (!Objects.equals(this.correct_answer, other.correct_answer)) {
            return false;
        }

        if (!Objects.equals(this.incorrect_answers, other.incorrect_answers)) {
            return false;
        }
        return true;
    }

    

    
 
    

    @Override
    public int hashCode() {
        return Objects.hash(question_id, category, type, difficulty, question, correct_answer, incorrect_answers, available);
    }

}
