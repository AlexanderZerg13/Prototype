package ru.infocom_s.propotype.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class LessonLab {
    private static LessonLab sLessonLab;

    private Context mAppContext;
    private ArrayList<Lesson> mLessons;


    private LessonLab(Context appContext) {
        mAppContext = appContext;
        mLessons = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i <12; i++) {
            int k = random.nextInt(3) + 3;
            int start = random.nextInt(4) + 1;
            for (int j = 0; j < k; j++) {
                mLessons.add(new Lesson(start + j,
                        StaticData.classes[random.nextInt(StaticData.classes.length)],
                        StaticData.audiences[random.nextInt(StaticData.audiences.length)],
                        StaticData.teachers[random.nextInt(2)],
                        i));
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

    public Lesson getLessonById(UUID uuid) {
        for (Lesson lesson: mLessons) {
            if (lesson.getId().equals(uuid)) {
                return lesson;
            }
        }
        return null;
    }
}
