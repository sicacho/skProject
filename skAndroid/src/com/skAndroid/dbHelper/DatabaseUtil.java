package com.skAndroid.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.skAndroid.common.Common;
import com.skAndroid.dto.Counter;
import com.skAndroid.dto.Notification;
import com.skAndroid.dto.Reminder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangtnse60992 on 11/9/2014.
 */
public class DatabaseUtil {
    private SQLiteDatabase database;
    private DatabaseHelper helper;
    private String[] allColumnCounter = {"_id", "idDetail", "score", "times", "target"};
    private String[] allColumnReminder = {"_id", "idCounter", "sentence", "sentenceTrans", "type"};

    public DatabaseUtil(Context context) {
        helper = new DatabaseHelper(context);
    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public Counter checkLessonDetail(int idDetail) {
        open();
        Counter counter = null;
        String selectQuery = "SELECT * FROM counter WHERE idDetail=?";
        Cursor c = database.rawQuery(selectQuery, new String[]{"" + idDetail});
        if (null == c) {
            return null;
        }
        if (c.moveToFirst()) {
            counter = cursoToCountingDTO(c);
        }
        database.close();
        return counter;
    }

    public Reminder checkReminder(int idCounter) {
        open();
        Reminder reminder = null;
        String selectQuery = "SELECT * FROM " + Common.REMINDER_TABLE + " WHERE idCounter=?";
        Cursor c = database.rawQuery(selectQuery, new String[]{"" + idCounter});
        if (null == c) {
            return null;
        }
        if (c.moveToFirst()) {
            reminder = cursoToReminderDTO(c);
        }
        database.close();
        return reminder;
    }

    public List<Integer> getListIdReminder() {
        open();
        List<Integer> listReminder = new ArrayList<Integer>();
        Reminder reminder = null;
        String selectQuery = "SELECT _id FROM " + Common.REMINDER_TABLE;
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        if (null == c) {
            return null;
        }
        while (!c.isAfterLast()) {
            int i = c.getInt(0);
            listReminder.add(i);
        }
        database.close();
        return listReminder;
    }

    public Reminder getReminder(int id) {
        open();
        Reminder reminder = null;
        String selectQuery = "SELECT * FROM " + Common.REMINDER_TABLE + " WHERE _id=?";
        Cursor c = database.rawQuery(selectQuery, new String[]{"" + id});
        if (null == c) {
            return null;
        }
        if (c.moveToFirst()) {
            reminder = cursoToReminderDTO(c);
        }
        database.close();
        return reminder;
    }

    public Notification getRandomNotification() {
        open();
        Notification notification = null;
        String randomIdQuery = "(SELECT _id FROM " + Common.REMINDER_TABLE + " ORDER BY RANDOM() LIMIT 1)";
        String selectQuery = "SELECT a._id,a.idCounter,a.sentence,a.sentenceTrans,b.score,b.times,a.type,b.target FROM " + Common.REMINDER_TABLE + " a JOIN " + Common.COUNTER_TABLE + " b  WHERE a._id=" + randomIdQuery + " AND a.idCounter = b._id AND b.times < b.target";
        Cursor c = database.rawQuery(selectQuery, null);
        if (null == c) {
            return null;
        }
        if (c.moveToFirst()) {
            notification = cursoToNotificationDTO(c);
        }
        database.close();
        return notification;
    }

    private Counter cursoToCountingDTO(Cursor cursor) {
        Counter counter = new Counter(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        return counter;
    }

    private Reminder cursoToReminderDTO(Cursor cursor) {
        Reminder reminder = new Reminder(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
        return reminder;
    }

    private Notification cursoToNotificationDTO(Cursor cursor) {
        Notification notification = new Notification(cursor.getInt(0), cursor.getInt(1),
                cursor.getString(2), cursor.getString(3), cursor.getInt(4),
                cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
        return notification;
    }

    public int updateCounting(Counter counter) {
        open();
        ContentValues values = new ContentValues();
        values.put("idDetail", counter.getIdDetail());
        values.put("score", counter.getScore());
        values.put("times", counter.getTimes());
        values.put("target", counter.getTarget());
        int i = database.update(Common.COUNTER_TABLE, values, "_id = ?", new String[]{"" + counter.getId()});
        database.close();
        return i;
    }

    public int updateCountingNotifi(int _id, Integer score, int time) {
        open();
        ContentValues values = new ContentValues();
        values.put("score", score);
        values.put("times", time);
        int i = database.update(Common.COUNTER_TABLE, values, "_id = ?", new String[]{"" + _id});
        database.close();
        return i;
    }

    public int addCounting(Counter counter) {
        open();
        ContentValues values = new ContentValues();
        values.put("idDetail", counter.getIdDetail());
        values.put("score", counter.getScore());
        values.put("times", counter.getTimes());
        values.put("target", counter.getTarget());
        Long id = database.insert(Common.COUNTER_TABLE, null, values);
        database.close();
        return id.intValue();
    }

    public void addReminder(Reminder reminder) {
        open();
        ContentValues values = new ContentValues();
        values.put("idCounter", reminder.idCouter);
        values.put("sentence", reminder.sentence);
        values.put("sentenceTrans", reminder.sentenceTrans);
        values.put("type", reminder.type);
        database.insert(Common.REMINDER_TABLE, null, values);
        database.close();
    }

    public int removeReminder(int reminder) {
        open();
        int i = database.delete(Common.REMINDER_TABLE, "_id = ?", new String[]{String.valueOf(reminder)});
        database.close();
        return i;
    }

    public List<Reminder> getAllReminder() {
        open();
        List<Reminder> listReminder = new ArrayList<Reminder>();
        Cursor cursor = database.query("reminder", allColumnReminder, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Reminder reminderDTO = cursoToReminderDTO(cursor);
            listReminder.add(reminderDTO);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return listReminder;
    }
}
