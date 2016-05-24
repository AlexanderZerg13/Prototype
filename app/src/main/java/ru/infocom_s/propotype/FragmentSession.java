package ru.infocom_s.propotype;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ru.infocom_s.propotype.data.SessionLesson;
import ru.infocom_s.propotype.data.SessionLessonLab;

public class FragmentSession extends ListFragment {
    private ArrayList<SessionLesson> mSessionLessons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Сессии");
        mSessionLessons = SessionLessonLab.get(getActivity()).getSessionLessons();
        setListAdapter(new SessionLessonAdapter(mSessionLessons));
    }

    private class SessionLessonAdapter extends ArrayAdapter<SessionLesson> {

        public SessionLessonAdapter(ArrayList<SessionLesson> lessons) {
            super(getActivity(), 0, lessons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.session_lesson_item, null);
            }

            SessionLesson sessionLesson = getItem(position);
            PreferenceManager.getDefaultSharedPreferences(getActivity());
            TextView sLessonTVName = (TextView) convertView.findViewById(R.id.sLessonTVName);
            TextView sLessonTVDate = (TextView) convertView.findViewById(R.id.sLessonTVDate);
            TextView sLessonTVRoom = (TextView) convertView.findViewById(R.id.sLessonTVRoom);
            TextView sLessonTVTeacher = (TextView) convertView.findViewById(R.id.sLessonTVTeacher);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy\nHH:mm");
            sLessonTVName.setText(sessionLesson.getName());
            sLessonTVDate.setText(sdf.format(sessionLesson.getDate()));
            sLessonTVRoom.setText(sessionLesson.getRoom());
            sLessonTVTeacher.setText(sessionLesson.getTeacher());

            return convertView;
        }
    }
}
