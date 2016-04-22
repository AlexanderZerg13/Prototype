package ru.infocom_s.propotype.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;

public class LessonLab {
    private static LessonLab sLessonLab;

    private Context mAppContext;
    private ArrayList<Lesson> mLessons;
    private String[] uroki = {"Защита информации в банковской системе и в электронном бизнесе" ,
            "Компьютерная и инженерная графика компьютерных систем",
            "Защита в операционных системах",
            "Физическая культура",
            "Компьютерные сети"};
    private String[] aud = {"9-402А", "9-405", "9-310", "9-410"};

    private LessonLab(Context appContext) {
        mAppContext = appContext;
        mLessons = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i <12; i++) {
            int k = random.nextInt(3) + 3;
            int start = random.nextInt(4) + 1;
            for (int j = 0; j < k; j++) {
                mLessons.add(new Lesson(start + j, uroki[random.nextInt(uroki.length)], aud[random.nextInt(aud.length)], i));
            }
        }
    }

    public static LessonLab get(Context c) {
        if (sLessonLab == null) {
            sLessonLab = new LessonLab(c);
        }
        return sLessonLab;
    }

    public ArrayList<Lesson> getLessonsByDay(int day) {
        ArrayList<Lesson> lessons = new ArrayList<>();

        for (Lesson lesson: mLessons) {
            if (lesson.getDay() == day) {
                lessons.add(lesson);
            }
        }

        return lessons;
    }
}
