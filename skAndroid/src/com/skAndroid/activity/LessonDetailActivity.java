package com.skAndroid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.skAndroid.R;
import com.skAndroid.common.Common;
import com.skAndroid.dbHelper.DatabaseUtil;
import com.skAndroid.dto.Counter;
import com.skAndroid.dto.Lesson;
import com.skAndroid.dto.LessonDetail;
import com.skAndroid.dto.Reminder;
import com.skAndroid.listView.LessonDetailViewBase;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by khangtnse60992 on 11/4/2014.
 */
public class LessonDetailActivity extends Activity implements RecognitionListener {

    ArrayList<LessonDetail> lessonDetails;
    Lesson lesson;
    ProgressDialog progDialog;
    ListView lv;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    String txtSpeak;
    private ProgressBar progressBar;
    LessonDetail lessonDetail;
    MediaPlayer mPlayer;
    TextToSpeech ttobj;
    DatabaseUtil databaseUtil;
    int flagSpeech = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        lesson = intent.getParcelableExtra("lesson");
        setContentView(R.layout.lessondetail);
        databaseUtil = new DatabaseUtil(this);
        TextView lessonName = (TextView) findViewById(R.id.textLessonName);
        TextView lessonNumber = (TextView) findViewById(R.id.textLessonNumber);
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lessonName.setText(lesson.getLessonName());
        lessonNumber.setText("Lesson " + String.valueOf(lesson.getLessonNumber()));
        new HttpRequestTask().execute();
//        if (mPlayer == null) {
//            mPlayer = new MediaPlayer();
//            ttobj = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
//                    if (status == TextToSpeech.SUCCESS) {
//                        int result = ttobj.setLanguage(Locale.US);
//                    } else {
//                        Log.e("TTS", "Initilization Failed!");
//                    }
//
//                }
//            });
//        }
        speechToText();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (speech != null) {
            speech.destroy();
        }
        if (ttobj != null) {
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onPause();

    }

    @Override
    public void onBeginningOfSpeech() {
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        progressBar.setIndeterminate(true);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        progressBar.setIndeterminate(false);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
    }

    @Override
    public void onPartialResults(Bundle arg0) {
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
    }

    @Override
    public void onResults(Bundle results) {
        flagSpeech = 0;
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        txtSpeak = matches.get(0);
        lessonDetail.setSentenceSpeak(txtSpeak);
        if (lessonDetail.getCounter() == null) {
            Counter counter = new Counter();
            lessonDetail.setCounter(counter);
            lessonDetail.getCounter().setIdDetail(lessonDetail.getId());
        }
        if (lessonDetail.getType() == Common.VOCABULARY) {
            lessonDetail.getCounter().setScore(LessonDetail.scoreAnalyzing(lessonDetail.getSentence(), lessonDetail.getSentenceSpeak()));
        } else if (lessonDetail.getType() == Common.QUESTION_ANSWER) {
            lessonDetail.getCounter().setScore(LessonDetail.scoreAnalyzing(lessonDetail.getSentenceTrans(), lessonDetail.getSentenceSpeak()));
        }
        int num = 0;
        if (lessonDetail.getCounter().getTimes() != null) {
            num = lessonDetail.getCounter().getTimes();
        }
        num++;
        lessonDetail.getCounter().setTimes(num);
        progressBar.setIndeterminate(false);
        if (lessonDetail.getCounter().getTarget() != null) {
            if (lessonDetail.getCounter().getTimes() == lessonDetail.getCounter().getTarget()) {
                Toast.makeText(this, "You reached target", Toast.LENGTH_SHORT).show();
                lessonDetail.remind = false;
            }
        }
        ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    public void speechToText() {
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                Common.LANGUAGE);

    }


    private class HttpRequestTask extends AsyncTask<Void, Void, LessonDetail[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog = new ProgressDialog(LessonDetailActivity.this);
            progDialog.setMessage("Loading...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
            if (mPlayer == null) {
                //      mPlayer = new MediaPlayer();
                ttobj = new TextToSpeech(LessonDetailActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = ttobj.setLanguage(Locale.US);
                        } else {
//                            Log.e("TTS", "Initilization Failed!");
                        }

                    }
                });
            }
        }

        @Override
        protected LessonDetail[] doInBackground(Void... params) {
            LessonDetail[] lessonDetails = null;
            try {

                String url = Common.LESSONDETAILURL + lesson.getId();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                lessonDetails = restTemplate.getForObject(url, LessonDetail[].class);
            } catch (Exception e) {
//                Log.e("LessonDetailActivity", e.getMessage(), e);
            }

            return lessonDetails;
        }

        @Override
        protected void onPostExecute(LessonDetail[] lessonDetailJson) {
            if (lessonDetailJson != null) {
                lessonDetails = new ArrayList<LessonDetail>();
                Counter counter;
                LessonDetail lessonDetai1;
                //check counter and remind
                for (int i = 0; i < lessonDetailJson.length; i++) {
                    lessonDetails.add(lessonDetailJson[i]);
                    counter = databaseUtil.checkLessonDetail(lessonDetailJson[i].getId());
                    if (counter != null) {
                        lessonDetails.get(i).setCounter(counter);
                        if (databaseUtil.checkReminder(lessonDetails.get(i).getCounter().getId()) != null) {
                            lessonDetails.get(i).remind = true;
                        } else {
                            lessonDetails.get(i).remind = false;
                        }

                    }
                }
                lv = (ListView) findViewById(R.id.listDetailView);
                lv.setAdapter(new LessonDetailViewBase(LessonDetailActivity.this, lessonDetails));
                progDialog.dismiss();
            } else {

            }
        }
    }

    public void onSpeakHandle(View view) {
        LinearLayout vwParentRow = (LinearLayout) view.getParent();
        progressBar = (ProgressBar) vwParentRow.getChildAt(1);
        int positionForView = lv.getPositionForView(vwParentRow);
        lessonDetail = lessonDetails.get(positionForView);
        if (flagSpeech == 0) {
            try {
                speech.startListening(recognizerIntent);
                progressBar.setIndeterminate(true);
                flagSpeech = 1;
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            }
        } else {
            speech.cancel();
            speech.startListening(recognizerIntent);
            progressBar.setIndeterminate(true);
            flagSpeech = 0;
        }
    }

    public void onCheckRemind(View view) {
        LinearLayout vwParentRow = (LinearLayout) view.getParent();
        CheckBox ck = (CheckBox) vwParentRow.getChildAt(3);
        int positionForView = lv.getPositionForView(vwParentRow);
        lessonDetail = lessonDetails.get(positionForView);
        if (lessonDetail.getCounter() == null) {
            ck.setChecked(false);
            Toast.makeText(this, "You must speak at least one time and set target", Toast.LENGTH_LONG).show();
        } else {
            if (lessonDetail.getCounter().getTarget() == null) {
                ck.setChecked(false);
                Toast.makeText(this, "You must speak at least one time and set target", Toast.LENGTH_LONG).show();
            } else {
                if (lessonDetail.getCounter().getTimes() >= lessonDetail.getCounter().getTarget()) {
                    ck.setChecked(false);
                    Toast.makeText(this, "You must speak at least one time and set target", Toast.LENGTH_LONG).show();
                } else {
                    if (ck.isChecked()) {
                        lessonDetail.remind = true;
                        if (lessonDetail.getCounter().getTarget() == null) {
                            onSetTargetHandle(view);
                        }
                    } else {
                        lessonDetail.remind = false;
                    }
                }
            }
        }
    }

    public void onListenHandle(View view) {
        LinearLayout vwParentRow = (LinearLayout) view.getParent();
        int positionForView = lv.getPositionForView(vwParentRow);
        lessonDetail = lessonDetails.get(positionForView);

        if (lessonDetail.getVoiceUrl() == null) {
            ttobj.speak(lessonDetail.getSentence(), TextToSpeech.QUEUE_FLUSH, null);
        } else {

            mPlayer.reset();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mPlayer.setDataSource(lessonDetail.getVoiceUrl());
            } catch (IllegalArgumentException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mPlayer.prepare();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            }
            mPlayer.start();
        }
    }

    public void onSetTargetHandle(View view) {
        LinearLayout vwParentRow = (LinearLayout) view.getParent();
        int positionForView = lv.getPositionForView(vwParentRow);
        lessonDetail = lessonDetails.get(positionForView);
        if (lessonDetail.getCounter() != null) {

            LayoutInflater li = LayoutInflater.from(LessonDetailActivity.this);
            View promptsView = li.inflate(R.layout.prompts, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    LessonDetailActivity.this);
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            final NumberPicker userInput = (NumberPicker) promptsView
                    .findViewById(R.id.numberPicker);
            userInput.setMaxValue(999);

                if (lessonDetail.getCounter().getTarget() != null) {
                    userInput.setValue(lessonDetail.getCounter().getTarget());
                    userInput.setMinValue(lessonDetail.getCounter().getTimes() + 1);
                }

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    lessonDetail.getCounter().setTarget(userInput.getValue());
                                    ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            Toast.makeText(this, "You must speak at least one time", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Saving data...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(true);
        progDialog.show();
        DatabaseUtil databaseUtil = new DatabaseUtil(this);
        Reminder reminder;
        for (int i = 0; i < lessonDetails.size(); i++) {
            if (lessonDetails.get(i).getCounter() != null && lessonDetails.get(i).getCounter().getId() != 0) {
                int row = databaseUtil.updateCounting(lessonDetails.get(i).getCounter());
                reminder = databaseUtil.checkReminder(lessonDetails.get(i).getCounter().getId());
                // if reminder hasn't created yet and checkBox = true then create reminder , else nothing happen
                if (lessonDetails.get(i).remind == true) {
                    if (reminder == null) {
                        reminder = new Reminder();
                        reminder.idCouter = lessonDetails.get(i).getCounter().getId();
                        reminder.sentence = lessonDetails.get(i).getSentence();
                        reminder.sentenceTrans = lessonDetails.get(i).getSentenceTrans();
                        reminder.type = lessonDetails.get(i).getType();
                        databaseUtil.addReminder(reminder);
                    }
                } else {
                    // if reminder has created already and checkBox = false then remove it , else nothing happe
                    if (reminder != null) {
                        databaseUtil.removeReminder(reminder.id);
                    }
                }
            } else if (lessonDetails.get(i).getCounter() != null && lessonDetails.get(i).getCounter().getId() == 0) {
                int id = databaseUtil.addCounting(lessonDetails.get(i).getCounter());
                if (lessonDetails.get(i).remind == true) {
                    reminder = new Reminder();
                    reminder.idCouter = id;
                    reminder.sentence = lessonDetails.get(i).getSentence();
                    reminder.sentenceTrans = lessonDetails.get(i).getSentenceTrans();
                    reminder.type = lessonDetails.get(i).getType();
                    databaseUtil.addReminder(reminder);
                }
            }
        }
        progDialog.dismiss();
        super.onBackPressed();
    }

}
