package com.skAndroid.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.skAndroid.R;

/**
 * Created by khangtnse60992 on 11/24/2014.
 */
public class GuideActivity  extends Activity{
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void howtouseHandle(View v) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.prompthowtouse);
        dialog.setTitle("How to use ?");
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void contactUsHandle(View v) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.promptcontactus);
        dialog.setTitle("Contact Us");
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void createCourseHandle(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lazyeng.com/service/course/1"));
        startActivity(i);
    }
}

