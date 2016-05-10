package ru.infocom_s.propotype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import ru.infocom_s.propotype.data.LessonProgress;
import ru.infocom_s.propotype.data.LessonProgressLab;

public class FragmentProgressLesson extends ListFragment {
    private ArrayList<LessonProgress> mLessonProgress;

    private static final String DIALOG_LESSON_PROGRESS_INFORMATION = "dialog_lesson_progress_information";

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        UUID uuid = ((LessonProgressAdapter) getListAdapter()).getItem(position).getId();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ProgressLessonInformationFragment progressLessonInformationFragment =
                ProgressLessonInformationFragment.newInstance(uuid);
        progressLessonInformationFragment.show(fragmentManager, DIALOG_LESSON_PROGRESS_INFORMATION);
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

    public static class ProgressLessonInformationFragment extends DialogFragment {

        private static final String EXTRA_ID =
                "ru.infocom_s.propotype.extra_id";

        public static ProgressLessonInformationFragment newInstance(UUID uuid) {

            Bundle args = new Bundle();
            args.putSerializable(EXTRA_ID, uuid);
            ProgressLessonInformationFragment fragment = new ProgressLessonInformationFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_lesson_progress_information, null);

            UUID uuid = (UUID) getArguments().getSerializable(EXTRA_ID);
            LessonProgress lessonProgress =
                    LessonProgressLab.get(getActivity()).getLessonProgressById(uuid);

            View viewHeader = getActivity().getLayoutInflater().inflate(R.layout.dialog_lesson_progress_information_header, null);

            TextView dlpTVName = (TextView) viewHeader.findViewById(R.id.dlpTVName);
            TextView dlpTVTeacher = (TextView) viewHeader.findViewById(R.id.dlpTVTeacher);
            ListView dlpListView = (ListView) v.findViewById(R.id.dlpLVMarks);

            dlpTVName.setText(lessonProgress.getName());
            dlpTVTeacher.setText(lessonProgress.getTeacher());
            dlpListView.setAdapter(new MarksAdapter(lessonProgress.getMarks()));
            dlpListView.addHeaderView(viewHeader, null, false);

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }

        private class MarksAdapter extends ArrayAdapter<LessonProgress.Mark> {

            public MarksAdapter(ArrayList<LessonProgress.Mark> marks) {
                super(getActivity(), 0, marks);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.lesson_progress_describe_item, null);
                }

                LessonProgress.Mark mark = getItem(position);

                TextView lpdiTVDate = (TextView) convertView.findViewById(R.id.lpdiTVDate);
                TextView lpdiTVMark = (TextView) convertView.findViewById(R.id.lpdiTVMark);
                TextView lpdiTVType = (TextView) convertView.findViewById(R.id.lpdiTVType);

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru"));
                String date = dateFormat.format(mark.getDate()) + ":";
                lpdiTVDate.setText(date);

                int rat = mark.getRat();
                SpannableString spannableString = new SpannableString(rat == 0 ? "н" : String.valueOf(rat));
                switch (rat) {
                    case 2:
                        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, 0);
                        break;
                    case 5:
                        spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, 0);
                        break;
                }
                lpdiTVMark.setText(spannableString, TextView.BufferType.SPANNABLE);

                lpdiTVType.setText(mark.getmTypeOfLesson());

                return convertView;
            }
        }
    }
}
