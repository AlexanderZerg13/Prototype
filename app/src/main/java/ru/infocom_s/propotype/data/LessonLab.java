package ru.infocom_s.propotype.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class LessonLab {
    private static LessonLab sLessonLab;

    private Context mAppContext;
    private ArrayList<Lesson> mLessons;
    private String[] classes = {"Защита информации в банковской системе и в электронном бизнесе" ,
            "Компьютерная и инженерная графика компьютерных систем",
            "Защита в операционных системах",
            "Физическая культура",
            "Компьютерные сети",
            "Криптографические методы защиты информации",
            "Защита программ и данных"};
    private String[] audiences = {"9-402А", "9-405", "9-310", "9-410"};
    private String[] teachers = {"Кирилов Сергей Владимирович", "Спичкин Василий Дмитриевич"};

    private LessonLab(Context appContext) {
        mAppContext = appContext;
        mLessons = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i <12; i++) {
            int k = random.nextInt(3) + 3;
            int start = random.nextInt(4) + 1;
            for (int j = 0; j < k; j++) {
                mLessons.add(new Lesson(start + j,
                        classes[random.nextInt(classes.length)],
                        audiences[random.nextInt(audiences.length)],
                        teachers[random.nextInt(2)],
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
