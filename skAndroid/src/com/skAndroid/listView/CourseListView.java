package com.skAndroid.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.skAndroid.R;
import com.skAndroid.dto.Course;

import java.util.ArrayList;

/**
 * Created by khangtnse60992 on 10/20/2014.
 */
public class CourseListView extends BaseAdapter {

    private ArrayList<Course> courses;
    private Context context;
    public CourseListView(Context context,ArrayList<Course> courses) {
        this.courses = courses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RecordHolder holder = null;
        if(convertView==null) {
            holder = new RecordHolder();
            convertView = inflater.inflate(R.layout.course_listview, null);
            holder.courseName = (TextView) convertView.findViewById(R.id.courseName);
            holder.courseAuthor = (TextView) convertView.findViewById(R.id.courseAuthor);
            convertView.setTag(holder);

            //  Lesson lesson = lessons.get(position);
        } else {
            //gridView = (View) convertView;
            holder = (RecordHolder) convertView.getTag();
        }
        holder.courseName.setText(courses.get(position).getName());
        holder.courseAuthor.setText(courses.get(position).getUsername());
        return convertView;
    }
    private class RecordHolder {
        TextView courseName;
        TextView courseAuthor;
    }

}
