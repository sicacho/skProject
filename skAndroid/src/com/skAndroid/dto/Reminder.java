package com.skAndroid.dto;

/**
 * Created by khangtnse60992 on 11/16/2014.
 */
public class Reminder {
    public Integer id;
    public Integer idCouter;
    public String sentence;
    public String sentenceTrans;
    public Integer type;

    public Reminder(Integer id, Integer idCouter, String sentence, String sentenceTrans,Integer type) {
        this.id = id;
        this.idCouter = idCouter;
        this.sentence = sentence;
        this.sentenceTrans = sentenceTrans;
        this.type = type;
    }

    public Reminder(Integer idCouter, String sentence, String sentenceTrans,Integer type) {
        this.id = id;
        this.idCouter = idCouter;
        this.sentence = sentence;
        this.sentenceTrans = sentenceTrans;
    }

    public Reminder() {
    }
}
