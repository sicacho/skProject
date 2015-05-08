package com.skAndroid.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khangtnse60992 on 11/17/2014.
 */
public class Notification implements Parcelable {
    public int id;
    public int idCounter;
    public String sentence;
    public String sentenceTrans;
    public Integer score;
    public int times;
    public int type;
    public int target;

    public Notification(int id, int idCounter, String sentence, String sentenceTrans, Integer score, int times,int type,int target) {
        this.id = id;
        this.idCounter = idCounter;
        this.sentence = sentence;
        this.sentenceTrans = sentenceTrans;
        this.score = score;
        this.times = times;
        this.type = type;
        this.target = target;
    }

    public Notification() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idCounter);
        dest.writeString(sentence);
        dest.writeString(sentenceTrans);
        dest.writeInt(score);
        dest.writeInt(times);
        dest.writeInt(type);
        dest.writeInt(target);
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel source) {
            Notification notification = new Notification();
            notification.id = source.readInt();
            notification.idCounter = source.readInt();
            notification.sentence = source.readString();
            notification.sentenceTrans = source.readString();
            notification.score = source.readInt();
            notification.times = source.readInt();
            notification.type = source.readInt();
            notification.target = source.readInt();
            return notification;
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
