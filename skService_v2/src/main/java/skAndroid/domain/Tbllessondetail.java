package skAndroid.domain;

import javax.persistence.*;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */
@Entity
public class Tbllessondetail {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "lessonID",referencedColumnName = "id")
    @ManyToOne
    private Tbllesson lessonId;
    @Basic
    @Column(name = "sentence")
    private String sentence;
    @Basic
    @Column(name = "sentenceTrans")
    private String sentenceTrans;
    @Basic
    @Column(name = "voiceUrl")
    private String voiceUrl;

    @Basic
    @Column(name = "type")
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Tbllesson getLessonId() {
        return lessonId;
    }

    public void setLessonId(Tbllesson lessonId) {
        this.lessonId = lessonId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tbllessondetail that = (Tbllessondetail) o;

        if (id != that.id) return false;
        if (lessonId != null ? !lessonId.equals(that.lessonId) : that.lessonId != null) return false;
        if (sentence != null ? !sentence.equals(that.sentence) : that.sentence != null) return false;
        if (sentenceTrans != null ? !sentenceTrans.equals(that.sentenceTrans) : that.sentenceTrans != null)
            return false;
        if (voiceUrl != null ? !voiceUrl.equals(that.voiceUrl) : that.voiceUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lessonId != null ? lessonId.hashCode() : 0);
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        result = 31 * result + (sentenceTrans != null ? sentenceTrans.hashCode() : 0);
        result = 31 * result + (voiceUrl != null ? voiceUrl.hashCode() : 0);
        return result;
    }
}
