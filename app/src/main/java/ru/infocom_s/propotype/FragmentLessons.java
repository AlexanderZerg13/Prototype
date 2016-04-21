package ru.infocom_s.propotype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import ru.infocom_s.propotype.data.Lesson;
import ru.infocom_s.propotype.data.LessonLab;

public class FragmentLessons extends Fragment {

    private static final String DIALOG_DATE = "date";
    private static final String KEY_DAY_OF_WEEK = "day_of_week";

    private ArrayList<Lesson> mLessons;
    private int day;

    public static FragmentLessons newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(KEY_DAY_OF_WEEK, id);

        FragmentLessons fragment = new FragmentLessons();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        day = getArguments().getInt(KEY_DAY_OF_WEEK);
        mLessons = LessonLab.get(getActivity()).getLessonsByDay(day);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_oneday, container, false);
        ListView listView = (ListView) v.findViewById(R.id.scheduleListView);
        TextView textView = (TextView) v.findViewById(R.id.scheduleDateTextView);

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, day / 6);
        calendar.set(Calendar.DAY_OF_WEEK, day % 6 + 2);

        Locale locale = new Locale("ru","RU");
        SimpleDateFormat sdf = new SimpleDateFormat("cccc, d MMMM", locale);
        textView.setText(sdf.format(calendar.getTime()));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(fm, DIALOG_DATE);
            }
        });

        listView.setAdapter(new LessonAdapter(mLessons));

        return v;
    }

    private class LessonAdapter extends ArrayAdapter<Lesson> {
        public LessonAdapter(ArrayList<Lesson> lessons) {
            super(getActivity(), 0, lessons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity()
                        .getLayoutInflater().inflate(R.layout.one_lesson, parent, false);
            }

            Lesson lesson = getItem(position);

            TextView tvNumber = (TextView) convertView.findViewById(R.id.lessonTVNumber);
            TextView tvName = (TextView) convertView.findViewById(R.id.lessonTVName);
            TextView tvRoom = (TextView) convertView.findViewById(R.id.lessonTVRoom);

            tvNumber.setText(lesson.getNumber() + " пара");
            tvName.setText(lesson.getName());
            tvRoom.setText(lesson.getRoom());


            return convertView;
        }
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View v = getActivity().getLayoutInflater()
                    .inflate(R.layout.dialog_date, null);

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }
    }
}
