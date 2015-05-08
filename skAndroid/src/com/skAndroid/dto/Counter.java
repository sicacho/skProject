package com.skAndroid.dto;

/**
 * Created by khangtnse60992 on 11/10/2014.
 */
public class Counter {
    int id;
    Integer idDetail;
    Integer score;
    Integer times;
    Integer target;

    public Counter() {
    }

    public Counter(int id, Integer idDetail, Integer score, Integer times, Integer target) {
        this.id = id;
        this.idDetail = idDetail;
        this.score = score;
        this.times = times;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}
