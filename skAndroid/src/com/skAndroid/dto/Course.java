package com.skAndroid.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khangtnse60992 on 10/20/2014.
 */
public class Course implements Parcelable {
    int id ;
    String name;
    String username;
    String detail;

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(detail);
    }

    public static final Parcelable.Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            Course course = new Course();
            course.id = source.readInt();
            course.name = source.readString();
            course.username = source.readString();
            course.detail = source.readString();
            return course;
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
