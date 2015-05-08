package com.skAndroid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.skAndroid.R;
import com.skAndroid.common.Common;
import com.skAndroid.dto.Lesson;
import com.skAndroid.listView.LessonGridViewBase;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by khangtnse60992 on 10/25/2014.
 */
public class LessonActivity extends Activity {

    ProgressDialog progDailog;
    ArrayList<Lesson> lessonList;
    int page = 1;
    String id = "";
    GridView gv;
    Lesson[] checkPage;
    Button btnLoadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lessonList = new ArrayList<Lesson>();
        Intent intent = getIntent();
        id = intent.getStringExtra("courseId");
        String courseName = intent.getStringExtra("courseName");
        setContentView(R.layout.lesson);
        btnLoadMore = (Button) findViewById(R.id.btnLoadmores);
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        TextView txtCourseName = (TextView) findViewById(R.id.txtCourseName);
        txtCourseName.setText(courseName);
        new HttpRequestTask().execute();
//        btnLoadMore.setVisibility(View.INVISIBLE);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (checkPage.length > Common.numberPage) {
                    new LoadMoreData().execute();
//                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class LoadMoreData extends AsyncTask<Void, Void, Lesson[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(LessonActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected Lesson[] doInBackground(Void... params) {
            Lesson[] lessons = null;
            try {

                final String url = Common.LESSONURL + id + "/" + page;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                lessons = restTemplate.getForObject(url, Lesson[].class);
                return lessons;
            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
            }
            return lessons;
        }

        @Override
        protected void onPostExecute(Lesson[] lessonsJson) {
            if (lessonsJson != null) {

                if (lessonsJson.length > Common.NUMBERPAGE) {
                    for (int i = 0; i < lessonsJson.length - 1; i++) {
                        lessonList.add(lessonsJson[i]);
                    }
                } else {
                    for (int i = 0; i < lessonsJson.length; i++) {
                        lessonList.add(lessonsJson[i]);
                    }
                }
               // checkPage = lessonsJson;
                if (lessonsJson.length > Common.NUMBERPAGE) {
                    btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    btnLoadMore.setVisibility(View.INVISIBLE);
                }
                GridView gv = (GridView) findViewById(R.id.gridViewLesson);
                gv.setAdapter(new LessonGridViewBase(LessonActivity.this, lessonList));
                page++;
                progDailog.dismiss();

            } else {
                progDailog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(LessonActivity.this);
                builder.setTitle("Error");
                builder.setMessage("Lost Connection");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //      setContentView(R.layout.course);
                        new HttpRequestTask().execute();
                    }
                });
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                builder.create();
                builder.show();
            }

        }


    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Lesson[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(LessonActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected Lesson[] doInBackground(Void... params) throws RestClientException {
            Lesson[] lessons = null;
            try {

                String url = Common.LESSONURL + id + "/" + page;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                lessons = restTemplate.getForObject(url, Lesson[].class);
                return lessons;
            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
            }

            return lessons;
        }

        @Override
        protected void onPostExecute(Lesson[] lessonsJson) {
            if (lessonsJson != null) {

                if (lessonsJson.length > Common.NUMBERPAGE) {
                    for (int i = 0; i < lessonsJson.length - 1; i++) {
                        lessonList.add(lessonsJson[i]);
                    }
                } else {
                    for (int i = 0; i < lessonsJson.length; i++) {
                        lessonList.add(lessonsJson[i]);
                    }
                }
//                checkPage = lessonsJson;
                if (lessonsJson.length > Common.NUMBERPAGE) {
//                    lessonList.add(new Lesson(0,"",0));
                    btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    btnLoadMore.setVisibility(View.INVISIBLE);
                }
                gv = (GridView) findViewById(R.id.gridViewLesson);
                gv.setAdapter(new LessonGridViewBase(LessonActivity.this, lessonList));

                page++;
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Lesson lesson = (Lesson) parent.getItemAtPosition(position);
                        Intent intent = new Intent(LessonActivity.this, LessonDetailActivity.class);
                        intent.putExtra("lesson", lesson);
                        startActivity(intent);
                    }
                });
                progDailog.dismiss();
            } else {
                progDailog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(LessonActivity.this);
                builder.setTitle("Error");
                builder.setMessage("Lost Connection");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //      setContentView(R.layout.course);
                        new HttpRequestTask().execute();
                    }
                });
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                builder.create();
                builder.show();
            }
//            gv.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                    int threshold = 1;
//                    int count = gv.getCount();
//                    if (scrollState == SCROLL_STATE_IDLE) {
//                        if (gv.getLastVisiblePosition() >= count
//                                - threshold) {
//                            // Execute LoadMoreDataTask AsyncTask
//                            if (checkPage.length > Common.numberPage) {
//                                new LoadMoreData().execute();
//                            }
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                }
//            });

        }


    }
}
