package ru.infocom_s.propotype;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

import ru.infocom_s.propotype.data.Lesson;
import ru.infocom_s.propotype.data.LessonLab;

public class FragmentLessons extends Fragment {

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_LESSON_INFORMATION = "date";
    private static final int REQUEST_DATE = 0;
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
        final ListView listView = (ListView) v.findViewById(R.id.scheduleListView);
        TextView textView = (TextView) v.findViewById(R.id.scheduleDateTextView);

        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, day / 6);
        calendar.set(Calendar.DAY_OF_WEEK, day % 6 + 2);

        Locale locale = new Locale("ru", "RU");
        SimpleDateFormat sdf = new SimpleDateFormat("cccc, d MMMM", locale);
        textView.setText(sdf.format(calendar.getTime()));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(calendar.getTime());
                dialog.setTargetFragment(FragmentLessons.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        listView.setAdapter(new LessonAdapter(mLessons));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson lesson = (Lesson) listView.getAdapter().getItem(position);
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                LessonInformationFragment lessonInformationFragment
                        = LessonInformationFragment.newInstance(lesson.getId());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    lessonInformationFragment.setSharedElementEnterTransition(new DetailsTransition());
                    lessonInformationFragment.setEnterTransition(new Fade());

                    lessonInformationFragment.setSharedElementReturnTransition(new DetailsTransition());
                }

                fm.beginTransaction()
                        .replace(R.id.fragmentContainer, lessonInformationFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            ((changeViewPagerState) getTargetFragment()).setPage(date);
        }
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

    public interface changeViewPagerState {
        void setPage(Date date);
    }

    public static class DatePickerFragment extends DialogFragment {

        private static final String EXTRA_DATE =
                "ru.infocom_s.propotype.extra_date";

        private Date mDate;

        public static DatePickerFragment newInstance(Date date) {

            Bundle args = new Bundle();
            args.putSerializable(EXTRA_DATE, date);

            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setArguments(args);

            return fragment;
        }

        private void sendResult(int resultCode) {
            if (getTargetFragment() == null)
                return;

            Intent i = new Intent();
            i.putExtra(EXTRA_DATE, mDate);

            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);

            View v = getActivity().getLayoutInflater()
                    .inflate(R.layout.dialog_date, null);

            DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
            datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int yearCh, int monthCh, int dayCh) {
                    mDate = new GregorianCalendar(yearCh, monthCh, dayCh).getTime();

                    getArguments().putSerializable(EXTRA_DATE, mDate);
                }
            });

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendResult(Activity.RESULT_OK);
                        }
                    })
//                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }
    }

    public static class LessonInformationFragment extends Fragment {

        private static final String EXTRA_ID =
                "ru.infocom_s.propotype.extra_id";

        private static final String[] SCHEDULE_PAIR = {
                "8:15 - 9:45", "9:55 - 11:25", "11:35 - 13:05",
                "13:25 - 14:55", "15:05 - 16:35", "16:50 - 18:20",
                "18:30 - 20:00", "20:10 - 21:40"};

        public static LessonInformationFragment newInstance(UUID id) {

            Bundle args = new Bundle();
            args.putSerializable(EXTRA_ID, id);

            LessonInformationFragment fragment = new LessonInformationFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = getActivity().getLayoutInflater()
                    .inflate(R.layout.dialog_lesson_information, null);

            UUID uuid = (UUID) getArguments().getSerializable(EXTRA_ID);
            Lesson lesson = LessonLab.get(getActivity()).getLessonById(uuid);

            TextView textViewLessonName = (TextView) v.findViewById(R.id.dialog_lesson_name);
            TextView textViewLessonDate = (TextView) v.findViewById(R.id.dialog_lesson_date);
            TextView textViewLessonAudience = (TextView) v.findViewById(R.id.dialog_lesson_cabinet);
            TextView textViewLessonTeacher = (TextView) v.findViewById(R.id.dialog_lesson_teacher);

            textViewLessonName.setText(lesson.getName());
            String lessonDateString = SCHEDULE_PAIR[lesson.getNumber() - 1] + " (" + lesson.getNumber() + " пара)";
            textViewLessonDate.setText(lessonDateString);
            textViewLessonAudience.setText(lesson.getRoom());
            textViewLessonTeacher.setText(lesson.getTeacher());

            textViewLessonTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return v;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }
}
