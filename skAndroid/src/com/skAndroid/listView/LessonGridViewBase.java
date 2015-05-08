package com.skAndroid.listView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.skAndroid.R;
import com.skAndroid.dto.Lesson;

import java.util.ArrayList;

/**
 * Created by khangtnse60992 on 11/2/2014.
 */
public class LessonGridViewBase extends BaseAdapter {
    private Context context;
    private ArrayList<Lesson> lessons;

    public LessonGridViewBase(Context context, ArrayList<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Object getItem(int position) {
        return lessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      //  View gridView;
        RecordHolder holder = null;
        if (convertView == null) {
           // gridView = new View(context);
            holder = new RecordHolder();
            convertView = inflater.inflate(R.layout.lesson_gridview, null);
            holder.num = (TextView) convertView.findViewById(R.id.lessonNum);
//            holder.defaultImage = (ImageView)convertView.findViewById(R.id.defaultImage);
            convertView.setTag(holder);

          //  Lesson lesson = lessons.get(position);
        } else {
            //gridView = (View) convertView;
            holder = (RecordHolder) convertView.getTag();
        }
        holder.num.setText(String.valueOf(lessons.get(position).getLessonNumber()));
        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/jersey.ttf");
        holder.num.setTypeface(type);
//        if(position==lessons.size()-1 && lessons.get(position).getId()==0) {
//            holder.defaultImage.setImageResource(R.drawable.next);
//            holder.num.setText("Load More");
//        }
        return convertView;
    }

    private class RecordHolder {
        TextView num;
//        ImageView defaultImage;
    }
}
