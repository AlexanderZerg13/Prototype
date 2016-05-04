package ru.infocom_s.propotype.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

public class SessionLessonLab {
    private static SessionLessonLab sSessionLessonLab;
    private ArrayList<SessionLesson> mSessionLessons;
    private Context mAppContext;

    private SessionLessonLab(Context appContext) {
        mAppContext = appContext;
        mSessionLessons = new ArrayList<>();

        Random random = new Random();
        int numberLessons = random.nextInt(3) + 3;
        for(int i = 0; i < numberLessons; i++) {
            mSessionLessons.add(new SessionLesson(
                    StaticData.classes[random.nextInt(StaticData.classes.length)],
                    StaticData.date[i],
                    StaticData.audiences[random.nextInt(StaticData.audiences.length)],
                    StaticData.teachers[random.nextInt(2)]
            ));
        }
    }

    public static SessionLessonLab get(Context c) {
        if (sSessionLessonLab == null) {
            sSessionLessonLab = new SessionLessonLab(c);
        }
        return sSessionLessonLab;
    }

    public ArrayList<SessionLesson> getSessionLessons() {
        return mSessionLessons;
    }
}
