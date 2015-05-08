package com.skAndroid.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.skAndroid.R;
import com.skAndroid.common.Common;
import com.skAndroid.dbHelper.DatabaseUtil;
import com.skAndroid.dto.Reminder;
import com.skAndroid.listView.ReminderListView;
import com.skAndroid.service.AlarmReceive;
import com.skAndroid.service.TTSService;
//import com.skAndroid.service.RemindService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangtnse60992 on 11/16/2014.
 */
public class ReminderActivity extends Activity implements View.OnClickListener {
    List<Reminder> reminders;
    ReminderListView reminderListView;
    ListView lv;
    AlarmManager alarmManager;
    //  public static List<Integer> idReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        lv = (ListView) findViewById(R.id.listReminder);
        Button startRemind = (Button) findViewById(R.id.btnActionRemind);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        if (checkRemind() == true) {
            startRemind.setText(Common.STOP_REMIND);
        } else {
            startRemind.setText(Common.START_REMIND);
        }

        getAllReminder();
        reminderListView = new ReminderListView(this, reminders);
        lv.setAdapter(reminderListView);
        startRemind.setOnClickListener(this);
    }

    public void getAllReminder() {
        DatabaseUtil databaseUtil = new DatabaseUtil(this);
        reminders = databaseUtil.getAllReminder();
    }

    //Using check remind are running or not
    public boolean checkRemind() {
        Intent intent = new Intent(ReminderActivity.this, AlarmReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                ReminderActivity.this, 234324243, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            return true;
        } else {
            return false;
        }
    }

    public void onRemoveReminderHandle(View view) {
        RelativeLayout vwParentRow = (RelativeLayout) view.getParent();
        final int positionForView = lv.getPositionForView(vwParentRow);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ReminderActivity.this);
        builder.setTitle("Confirm");
        builder.setMessage("You want remove this reminder");
        builder.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //      setContentView(R.layout.course);

            }
        });
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseUtil databaseUtil = new DatabaseUtil(ReminderActivity.this);
                databaseUtil.removeReminder(reminders.get(positionForView).id);
                reminders.remove(positionForView);
                ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
            }
        });
        builder.create();
        builder.show();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnActionRemind) {
            String action = ((Button) v).getText().toString();
            if (action.equals(Common.START_REMIND)) {
                if (reminders.size() == 0) {
                    Toast.makeText(this, "Please remind at least once sentence ", Toast.LENGTH_LONG).show();
                } else {
                    LayoutInflater li = LayoutInflater.from(ReminderActivity.this);
                    View promptsView = li.inflate(R.layout.repeat_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            ReminderActivity.this);
                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                    final NumberPicker userInput = (NumberPicker) promptsView
                            .findViewById(R.id.numberPickerRemind);
                    userInput.setMaxValue(60);
                    userInput.setMinValue(1);
                    final CheckBox checkAuto = (CheckBox) promptsView.findViewById(R.id.checkBoxAuto);
                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            int time = userInput.getValue();
                                            Intent intentService = new Intent(ReminderActivity.this, TTSService.class);
                                            if(checkAuto.isChecked()==true) {
                                                intentService.putExtra("autoSpeak",1);
                                            } else {
                                                intentService.putExtra("autoSpeak",0);
                                            }
                                            startService(intentService);
                                            Intent intent = new Intent(ReminderActivity.this, AlarmReceive.class);
                                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                                    ReminderActivity.this, 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time * 60 * 1000, pendingIntent);
//                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time * 1000, pendingIntent);
                                            Button startRemind = (Button) findViewById(R.id.btnActionRemind);
                                            startRemind.setText(Common.STOP_REMIND);

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
                }
            } else {
                //Stop Service
                if (checkRemind() == true) {
                    Intent intent = new Intent(ReminderActivity.this, AlarmReceive.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            ReminderActivity.this, 234324243, intent, PendingIntent.FLAG_NO_CREATE);
                    pendingIntent.cancel();
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    Button startRemind = (Button) findViewById(R.id.btnActionRemind);
                    startRemind.setText(Common.START_REMIND);
                    stopService(new Intent(ReminderActivity.this, TTSService.class));
                } else {
                    Button startRemind = (Button) findViewById(R.id.btnActionRemind);
                    startRemind.setText(Common.START_REMIND);
                }

            }
        } else {
            onBackPressed();
        }
    }
}
