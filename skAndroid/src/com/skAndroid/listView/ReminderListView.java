package com.skAndroid.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.skAndroid.R;
import com.skAndroid.dto.Reminder;

import java.util.List;

/**
 * Created by khangtnse60992 on 11/16/2014.
 */
public class ReminderListView extends BaseAdapter {
    Context context;
    List<Reminder> reminders;

    public ReminderListView(Context context, List<Reminder> reminders) {
        this.context = context;
        this.reminders = reminders;
    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Object getItem(int position) {
        return reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RecordHolder holder = null;
        if(convertView==null) {
            holder = new RecordHolder();
            convertView = inflater.inflate(R.layout.reminder_listview, null);
            holder.sentence = (TextView) convertView.findViewById(R.id.txtReminder);
            convertView.setTag(holder);
        } else {
            holder = (RecordHolder) convertView.getTag();
        }
        holder.sentence.setText(reminders.get(position).sentence);
        return convertView;
    }

    private class RecordHolder {
        TextView sentence;
    }
}
