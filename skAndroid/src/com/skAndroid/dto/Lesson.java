package com.skAndroid.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khangtnse60992 on 10/25/2014.
 */
public class Lesson  implements Parcelable {
    int id;
    String lessonName;
    int lessonNumber;

    public Lesson() {
    }

    public Lesson(int id, String lessonName, int lessonNumber) {
        this.id = id;
        this.lessonName = lessonName;
        this.lessonNumber = lessonNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lessonName);
        dest.writeInt(lessonNumber);
    }

    public static final Parcelable.Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel source) {
            Lesson lesson = new Lesson();
            lesson.id = source.readInt();
            lesson.lessonName = source.readString();
            lesson.lessonNumber = source.readInt();
            return lesson;
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };
}
