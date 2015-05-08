package com.skAndroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by khangtnse60992 on 11/23/2014.
 */
public class TTSService extends Service implements TextToSpeech.OnInitListener{

    public static TextToSpeech ttobj;
    public static int autoSpeak=0;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ttobj = new TextToSpeech(this,this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            autoSpeak = intent.getIntExtra("autoSpeak",0);
        }
        return START_STICKY;
    }

    @Override
    public void onLowMemory() {
        ttobj.shutdown();
    }

    @Override
    public void onDestroy() {
        ttobj.shutdown();
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttobj.setLanguage(Locale.US);
        } else {
//            Log.e("TTS", "Initilization Failed!");

        }
    }
}
