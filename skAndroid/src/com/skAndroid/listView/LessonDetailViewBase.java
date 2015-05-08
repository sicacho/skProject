package com.skAndroid.listView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.skAndroid.R;
import com.skAndroid.animation.CircularProgressBar;
import com.skAndroid.common.Common;
import com.skAndroid.dbHelper.DatabaseUtil;
import com.skAndroid.dto.Counter;
import com.skAndroid.dto.LessonDetail;
import com.skAndroid.dto.Reminder;

import java.util.ArrayList;

/**
 * Created by khangtnse60992 on 11/4/2014.
 */
public class LessonDetailViewBase extends BaseAdapter {
    private Context context;
    private ArrayList<LessonDetail> lessonDetails;
    private ListView lv;


    public LessonDetailViewBase(Context context, ArrayList<LessonDetail> lessonDetails) {
        this.context = context;
        this.lessonDetails = lessonDetails;
    }

    @Override
    public int getCount() {
        return lessonDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        if (convertView == null) {
            holder = new RecordHolder();
            convertView = inflater.inflate(R.layout.lessondetail_listview, null);
            holder.sentence = (TextView) convertView.findViewById(R.id.txtSentence);
            holder.sentenceTrans = (TextView) convertView.findViewById(R.id.txtSentenceTrans);
            holder.score = (CircularProgressBar) convertView.findViewById(R.id.scoreProgressBar);
            holder.numSpeak = (TextView) convertView.findViewById(R.id.txtNumberRead);
            holder.sentenceSpeak = (TextView) convertView.findViewById(R.id.txtSpeak);
            holder.speakLayout = (RelativeLayout) convertView.findViewById(R.id.layoutSpeaktext);
            holder.target = (Button) convertView.findViewById(R.id.btnTarget);
            holder.checkRemind = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (RecordHolder) convertView.getTag();
        }
        holder.sentence.setText(lessonDetails.get(position).getSentence());
        holder.sentenceTrans.setText(lessonDetails.get(position).getSentenceTrans());
        if(lessonDetails.get(position).getType()== Common.QUESTION_ANSWER) {
            holder.sentenceTrans.setTextColor(Color.parseColor("#FF879CFF"));
            holder.sentence.setTextColor(Color.BLACK);
        } else if (lessonDetails.get(position).getType()== Common.VOCABULARY) {
            holder.sentence.setTextColor(Color.parseColor("#FF879CFF"));
            holder.sentenceTrans.setTextColor(Color.BLACK);
        }
        // When update UI (lessonDetail has counter , but not save in dB)
        if (lessonDetails.get(position).getCounter() != null) {
//            holder.score.setText(String.valueOf(lessonDetails.get(position).getCounter().getScore()));
            final View view = convertView;
            final int positionProgressBar = position;
            holder.score.animateProgressTo(0,lessonDetails.get(position).getCounter().getScore(), new CircularProgressBar.ProgressAnimationListener() {

                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationProgress(int progress) {

                }

                @Override
                public void onAnimationFinish() {
                }
            });
            holder.score.setTitle(lessonDetails.get(position).getCounter().getScore()+"");
            holder.numSpeak.setText(String.valueOf(lessonDetails.get(position).getCounter().getTimes()));
            if (lessonDetails.get(position).getCounter().getTarget() == null) {
                holder.target.setText("?");
            } else {
                holder.target.setText(String.valueOf(lessonDetails.get(position).getCounter().getTarget()));
            }
            holder.checkRemind.setChecked(lessonDetails.get(position).remind);
        } else {
            holder.score.animateProgressTo(0,0, new CircularProgressBar.ProgressAnimationListener() {

                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationProgress(int progress) {

                }

                @Override
                public void onAnimationFinish() {
                }
            });
            holder.score.setTitle("0");
            holder.numSpeak.setText("0");
            holder.target.setText("?");
//            holder.speakLayout.setVisibility(View.INVISIBLE);
            holder.checkRemind.setChecked(lessonDetails.get(position).remind);
//            }

        }

        if (lessonDetails.get(position).getSentenceSpeak() == null) {
            holder.sentenceSpeak.setText("");
        } else {
            holder.sentenceSpeak.setText(lessonDetails.get(position).getSentenceSpeak());
//            holder.speakLayout.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class RecordHolder {
        TextView sentence;
        TextView sentenceTrans;
        CircularProgressBar score;
        TextView numSpeak;
        TextView sentenceSpeak;
        RelativeLayout speakLayout;
        Button target;
        CheckBox checkRemind;
    }

//    private View.OnClickListener speakClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    }
}
