package com.skAndroid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.skAndroid.R;
import com.skAndroid.common.Common;
import com.skAndroid.dto.Course;
import com.skAndroid.listView.CourseListView;
import com.skAndroid.util.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class CourseActivity extends Activity {
    CourseListView courses;
    Context context;
    ListView lv;
    ArrayList<Course> coursesList;
    Dialog dialog;
    ProgressDialog progDialog;
    Button btnLoadMore;
    int page = 1;
    String urlContext;
    String keyword;
    // ListView lv;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coursesList = new ArrayList<Course>();
        setContentView(R.layout.course);
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        ImageView btnSearch = (ImageView) findViewById(R.id.btnSearch);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(CourseActivity.this);
                View promptsView = li.inflate(R.layout.search_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        CourseActivity.this);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                final EditText searchText = (EditText) promptsView.findViewById(R.id.searchText);
                alertDialogBuilder.setCancelable(false).setTitle("Search by Course name")
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                keyword = searchText.getText().toString();
                                new LoadDataSearch().execute();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        context = this;
        new HttpRequestTask().execute();
        btnLoadMore = (Button) findViewById(R.id.btnLoadmores);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadMoreData().execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onDetailHandle(View view) {
        RelativeLayout vwParentRow = (RelativeLayout) view.getParent();
        int position = lv.getPositionForView(vwParentRow);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.promptcoursedetail);
        dialog.setTitle("How to Study " + coursesList.get(position).getName() + " ?");
        TextView courseDetail = (TextView) dialog.findViewById(R.id.courseDetailDialog);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnCourseDetail);
        courseDetail.setText(coursesList.get(position).getDetail());
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private class LoadMoreData extends AsyncTask<Void, Void, Course[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog = new ProgressDialog(CourseActivity.this);
            progDialog.setMessage("Loading...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected Course[] doInBackground(Void... params) {
            Course[] courses = null;
            try {

                final String url = urlContext + page;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                courses = restTemplate.getForObject(url, Course[].class);
                return courses;
            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
            }
            return courses;
        }

        @Override
        protected void onPostExecute(Course[] lessonsJson) {
            if (lessonsJson != null) {

                if (lessonsJson.length == Common.NUMBERPAGE) {
                    for (int i = 0; i < lessonsJson.length - 1; i++) {
                        coursesList.add(lessonsJson[i]);
                    }
                } else {
                    for (int i = 0; i < lessonsJson.length; i++) {
                        coursesList.add(lessonsJson[i]);
                    }
                }
                // checkPage = lessonsJson;
                if (lessonsJson.length > Common.NUMBERPAGE) {
                    btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    btnLoadMore.setVisibility(View.INVISIBLE);
                }
                lv = (ListView) findViewById(R.id.listCourse);
                courses = new CourseListView(context, coursesList);
                lv.setAdapter(courses);
                page++;
                progDialog.dismiss();

            } else {
                progDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
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


    private class HttpRequestTask extends AsyncTask<Void, Void, Course[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            page = 1;
            coursesList.clear();
            progDialog = new ProgressDialog(CourseActivity.this);
            progDialog.setMessage("Loading...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected Course[] doInBackground(Void... params) throws RestClientException {
            Course[] courses = null;
            try {
                final String url = Common.COURSEURL + Common.MAIN_COURSE + "/" + page;
                urlContext = Common.COURSEURL + Common.MAIN_COURSE + "/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                courses = restTemplate.getForObject(url, Course[].class);
                return courses;
            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
            }

            return courses;
        }

        @Override
        protected void onPostExecute(Course[] coursesJson) {
            if (coursesJson != null) {
                if (coursesJson.length == Common.NUMBERPAGE) {
                    for (int i = 0; i < coursesJson.length - 1; i++) {
                        coursesList.add(coursesJson[i]);
                    }
                } else {
                    for (int i = 0; i < coursesJson.length; i++) {
                        coursesList.add(coursesJson[i]);
                    }
                }
                // checkPage = lessonsJson;
                if (coursesJson.length > Common.NUMBERPAGE) {
                    btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    btnLoadMore.setVisibility(View.INVISIBLE);
                }
                lv = (ListView) findViewById(R.id.listCourse);
                courses = new CourseListView(context, coursesList);
                lv.setAdapter(courses);
                page++;
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Course course = (Course) parent.getItemAtPosition(position);
                        Intent intent = new Intent(context, LessonActivity.class);
                        intent.putExtra("courseId", String.valueOf(course.getId()));
                        intent.putExtra("courseName", course.getName());
                        startActivity(intent);
                    }
                });
                progDialog.dismiss();
            } else {
                progDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
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

    private class LoadDataSearch extends AsyncTask<Void, Void, Course[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            page = 1;
            coursesList.clear();
            progDialog = new ProgressDialog(CourseActivity.this);
            progDialog.setMessage("Loading...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected Course[] doInBackground(Void... params) throws RestClientException {
            Course[] courses = null;
            try {
                final String url = Common.COURSESEARCH + keyword + "/" + 1;
                urlContext = Common.COURSESEARCH + keyword + "/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                courses = restTemplate.getForObject(url, Course[].class);
                return courses;
            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
                progDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
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

            return courses;
        }

        @Override
        protected void onPostExecute(Course[] coursesJson) {
            if (coursesJson != null && coursesJson.length>0) {
                if (coursesJson.length == Common.NUMBERPAGE) {
                    for (int i = 0; i < coursesJson.length - 1; i++) {
                        coursesList.add(coursesJson[i]);
                    }
                } else {
                    for (int i = 0; i < coursesJson.length; i++) {
                        coursesList.add(coursesJson[i]);
                    }
                }
                // checkPage = lessonsJson;
                if (coursesJson.length > Common.NUMBERPAGE) {
                    btnLoadMore.setVisibility(View.VISIBLE);
                } else {
                    btnLoadMore.setVisibility(View.INVISIBLE);
                }
                lv = (ListView) findViewById(R.id.listCourse);
                courses = new CourseListView(context, coursesList);
                lv.setAdapter(courses);
                page++;
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Course course = (Course) parent.getItemAtPosition(position);
                        Intent intent = new Intent(context, LessonActivity.class);
                        intent.putExtra("courseId", String.valueOf(course.getId()));
                        intent.putExtra("courseName", course.getName());
                        startActivity(intent);
                    }
                });
                progDialog.dismiss();
            } else {
                progDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
                builder.setTitle("Result");
                builder.setMessage("Can't find any course");
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new HttpRequestTask().execute();
                    }
                });
                builder.create();
                builder.show();
            }

        }
    }
}
