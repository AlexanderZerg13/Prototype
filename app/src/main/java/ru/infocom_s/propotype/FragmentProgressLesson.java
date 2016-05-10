package ru.infocom_s.propotype;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.infocom_s.propotype.data.LessonProgress;
import ru.infocom_s.propotype.data.LessonProgressLab;

public class FragmentProgressLesson extends ListFragment {
    private ArrayList<LessonProgress> mLessonProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Успеваемость");
        mLessonProgress = LessonProgressLab.get(getActivity()).getLessonProgress();
        int numberMarks = 8;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            numberMarks = 15;
        }
        setListAdapter(new LessonProgressAdapter(mLessonProgress, numberMarks));
    }

    private class LessonProgressAdapter extends ArrayAdapter<LessonProgress> {
        private int mMarksCount;

        public LessonProgressAdapter(ArrayList<LessonProgress> lessonProgresses, int marksCount) {
            super(getActivity(), 0, lessonProgresses);
            mMarksCount = marksCount;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.lesson_progress_item, null);
            }

            LessonProgress lessonProgress = getItem(position);
            ArrayList<LessonProgress.Mark> marks = lessonProgress.getMarks();

            TextView lpTVName = (TextView) convertView.findViewById(R.id.lpTVName);
            TextView lpTVMarks = (TextView) convertView.findViewById(R.id.lpTVMarks);
            TextView lpTVMarksDate = (TextView) convertView.findViewById(R.id.lpTVMarksDate);

            SpannableStringBuilder ssb = new SpannableStringBuilder();

            if (marks.size() > mMarksCount) {
                ssb.append("... ");
            }
            int str;
            int iTo = mMarksCount < marks.size() ? mMarksCount : marks.size();
            int iFrom = marks.size() - iTo;
            for (int i = iFrom; i < marks.size(); i++) {
                int mark = marks.get(i).getRat();
                str = ssb.length();
                ssb.append(mark == 0 ? "н" : String.valueOf(mark));
                switch (mark) {
                    case 5:
                        ssb.setSpan(new ForegroundColorSpan(Color.GREEN), str, ssb.length(), 0);
                        break;
                    case 2:
                        ssb.setSpan(new ForegroundColorSpan(Color.RED), str, ssb.length(), 0);
                        break;
                }
//                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru"));
//                ssb.append("(" + dateFormat.format(marks.get(i).getDate()) + ")");
                if (i != marks.size() - 1) {
                    ssb.append(", ");
                }
            }

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru"));
            StringBuilder dates = new StringBuilder();
            dates.append(dateFormat.format(marks.get(iFrom).getDate()));
            dates.append(" - ");
            dates.append(dateFormat.format(marks.get(marks.size() - 1).getDate()));

            lpTVName.setText(lessonProgress.getName());
            lpTVMarks.setText(ssb, TextView.BufferType.SPANNABLE);
            lpTVMarksDate.setText(dates.toString());

            return convertView;
        }
    }
}
