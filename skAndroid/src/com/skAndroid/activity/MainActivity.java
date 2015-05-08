package com.skAndroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.skAndroid.R;

/**
 * Created by khangtnse60992 on 11/13/2014.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView mainCourse = (ImageView) findViewById(R.id.btnMainCourse);
        ImageView reminder = (ImageView) findViewById(R.id.btnMainReminder);
        ImageView howTo = (ImageView) findViewById(R.id.btnMainHowTo);
        mainCourse.setOnClickListener(this);
        reminder.setOnClickListener(this);
        howTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId()==R.id.btnMainCourse) {
             intent = new Intent(this,CourseActivity.class);
            startActivity(intent);
        } else if(v.getId()==R.id.btnMainReminder) {
             intent = new Intent(this,ReminderActivity.class);
            startActivity(intent);
        } else if(v.getId()==R.id.btnMainHowTo) {
             intent = new Intent(this,GuideActivity.class);
            startActivity(intent);
        }
    }
}
