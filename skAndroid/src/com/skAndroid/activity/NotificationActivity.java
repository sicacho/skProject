package com.skAndroid.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.skAndroid.R;
import com.skAndroid.animation.CircularProgressBar;
import com.skAndroid.common.Common;
import com.skAndroid.dbHelper.DatabaseUtil;
import com.skAndroid.dto.LessonDetail;
import com.skAndroid.dto.Notification;
import com.skAndroid.service.TTSService;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by khangtnse60992 on 11/16/2014.
 */
public class NotificationActivity extends Activity implements View.OnClickListener, RecognitionListener {

    Notification notification;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    String txtSpeak;
    private ProgressBar progressBar;
    private ProgressBar progressBarVolume;
    TextToSpeech ttobj;
    DatabaseUtil databaseUtil;
    int result;
//    ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Notification Manager
        super.onCreate(savedInstanceState);
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Dismiss Notification
        notificationmanager.cancel(0);
        setContentView(R.layout.notification);

        Intent intent = getIntent();

        notification = intent.getParcelableExtra("notification");
        // notification = getNotification(id);
        updateLayout(notification);
        speechToText();
//        ttobj = ;
        //      mPlayer = new MediaPlayer();
        //      new HttpRequestTask().execute();
    }

    public void updateLayout(Notification notification) {
        TextView tvSentence = (TextView) findViewById(R.id.txtSentence);
        TextView tvSentenceTrans = (TextView) findViewById(R.id.txtSentenceTrans);
        TextView times = (TextView) findViewById(R.id.txtNumberRead);
        CircularProgressBar score = (CircularProgressBar) findViewById(R.id.scoreProgressBar);
        Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
        Button btnListen = (Button) findViewById(R.id.btnListen);
        Button btnActionRemind = (Button) findViewById(R.id.btnNotiaction);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (notification.type == Common.QUESTION_ANSWER) {
            tvSentenceTrans.setTextColor(Color.parseColor("#FF879CFF"));
            tvSentence.setTextColor(Color.BLACK);
        } else if (notification.type == Common.VOCABULARY) {
            tvSentence.setTextColor(Color.parseColor("#FF879CFF"));
            tvSentenceTrans.setTextColor(Color.BLACK);
        }
        tvSentence.setText(notification.sentence);
        tvSentenceTrans.setText(notification.sentenceTrans);
        times.setText(String.valueOf(notification.times));
        score.animateProgressTo(0, notification.score, new CircularProgressBar.ProgressAnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationFinish() {

            }

            @Override
            public void onAnimationProgress(int progress) {
                CircularProgressBar score = (CircularProgressBar) findViewById(R.id.scoreProgressBar);
                score.setProgress(progress);
            }
        });
        score.setTitle(notification.score+"");
        btnListen.setOnClickListener(this);
        btnSpeak.setOnClickListener(this);
        btnActionRemind.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnListen) {
            TTSService.ttobj.speak(notification.sentence, TextToSpeech.QUEUE_FLUSH, null);
        } else if (v.getId() == R.id.btnSpeak) {
            speech.startListening(recognizerIntent);
            progressBar.setIndeterminate(true);
        } else {
            Button btnAction = (Button) findViewById(R.id.btnNotiaction);
            String action = btnAction.getText().toString();
            if (action.equals("Ignore")) {
                NotificationActivity.this.finish();
            } else {
                databaseUtil = new DatabaseUtil(NotificationActivity.this);
                databaseUtil.updateCountingNotifi(notification.idCounter, notification.score, notification.times);
                if(notification.times==notification.target) {
                    databaseUtil.removeReminder(notification.id);
                }
                NotificationActivity.this.finish();
            }
        }
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
    public void onReadyForSpeech(Bundle arg0) {

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
    public void onRmsChanged(float rmsdB) {
        progressBar.setProgress((int) rmsdB);
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        txtSpeak = matches.get(0);
        if (notification.type == Common.VOCABULARY) {
            notification.score = LessonDetail.scoreAnalyzing(notification.sentence, txtSpeak);
        } else if (notification.type == Common.QUESTION_ANSWER) {
            notification.score = LessonDetail.scoreAnalyzing(notification.sentenceTrans, txtSpeak);
        }
        notification.times++;
            if(notification.times==notification.target) {
                Toast.makeText(this,"You reached target",Toast.LENGTH_SHORT).show();
            }
        TextView tvSpeak = (TextView) findViewById(R.id.txtSpeak);
        CircularProgressBar score = (CircularProgressBar) findViewById(R.id.scoreProgressBar);
        TextView tvTimes = (TextView) findViewById(R.id.txtNumberRead);
        tvSpeak.setText(txtSpeak);
        score.animateProgressTo(0, notification.score, new CircularProgressBar.ProgressAnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationFinish() {

            }

            @Override
            public void onAnimationProgress(int progress) {
                CircularProgressBar score = (CircularProgressBar) findViewById(R.id.scoreProgressBar);
                score.setProgress(progress);
            }
        });
        score.setTitle(notification.score+"");
        tvTimes.setText(String.valueOf(notification.times));
        Button btnAction = (Button) findViewById(R.id.btnNotiaction);
        btnAction.setText("Save and Close");
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

//    private class HttpRequestTask extends AsyncTask<Void, Void,Integer > {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBarVolume = (ProgressBar) findViewById(R.id.progressBarVolume);
//            progressBarVolume.setIndeterminate(false);
//        }
//
//        @Override
//        protected Integer doInBackground(Void... params) {
//
//            ttobj = new TextToSpeech(NotificationActivity.this, new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
//                    if (status == TextToSpeech.SUCCESS) {
//                         result = ttobj.setLanguage(Locale.US);
//                    } else {
//                        Log.e("TTS", "Initilization Failed!");
//                        result = -100;
//                    }
//
//                }
//            });
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            if(integer!=-100) {
//                progressBarVolume.setIndeterminate(true);
//                progressBarVolume.setVisibility(View.INVISIBLE);
//                Button btnListen = (Button) findViewById(R.id.btnListen);
//                btnListen.setVisibility(View.VISIBLE);
//            } else {
//                Toast.makeText(NotificationActivity.this,"Has a error with speech , can't hear the sentence",Toast.LENGTH_LONG);
//            }
//            super.onPostExecute(integer);
//        }
//    }

}
