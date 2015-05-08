package com.skAndroid.dto;

/**
 * Created by khangtnse60992 on 11/4/2014.
 */
public class LessonDetail {
    int id;
    String sentence;
    String sentenceTrans;
    String voiceUrl;
    String sentenceSpeak;
    Counter counter;
    int type;
    public boolean remind;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentenceTrans() {
        return sentenceTrans;
    }

    public void setSentenceTrans(String sentenceTrans) {
        this.sentenceTrans = sentenceTrans;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getSentenceSpeak() {
        return sentenceSpeak;
    }

    public void setSentenceSpeak(String sentenceSpeak) {
        this.sentenceSpeak = sentenceSpeak;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static int scoreAnalyzing(String sentence,String sentenceSpeak) {
        String regex = "([^a-zA-Z']+)'*\\1*";
        String[] sentences = sentence.split(regex);
        String[] sentenceSpeaks = sentenceSpeak.split(regex);
        int score = 100/(sentences.length);
        int times = 0 ;

        if (sentence.equalsIgnoreCase(sentenceSpeak)) {
            return 100;
        } else {
            if (sentences.length >= sentenceSpeaks.length) {
                for (int i = 0; i < sentenceSpeaks.length; i++) {
                    if (sentences[i].equalsIgnoreCase(sentenceSpeaks[i])) {
                        times++;
                    }
                }
            } else {
                for (int i = 0; i < sentences.length; i++) {
                    if (sentences[i].equalsIgnoreCase(sentenceSpeaks[i])) {
                        times++;
                    }
                }
            }
        }

        return score*times;
    }
}
