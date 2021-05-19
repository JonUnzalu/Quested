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
public class Score {

    @JsonSerialize(using = ToStringSerializer.class)
    private int score_id;
    private int score;
    private String nickname;

    public int getScore_id() {
        return score_id;
    }

    public Score setScore_id(int score_id) {
        this.score_id = score_id;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Score setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Override
    public String toString() {
        return "Scoreboard{" + "score_id=" + score_id + ", score=" + score + ", user=" + nickname + '}';
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
        final Score other = (Score) obj;
        if (this.score_id != other.score_id) {
            return false;
        }
        if (this.score != other.score) {
            return false;
        }
        if (!Objects.equals(this.nickname, other.nickname)) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hash(score_id, score, nickname);
    }

}
