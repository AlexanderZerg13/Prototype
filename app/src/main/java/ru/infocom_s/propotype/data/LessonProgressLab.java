package ru.infocom_s.propotype.data;


import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Random;

public class LessonProgressLab {
    private static LessonProgressLab sLessonProgressLab;
    private ArrayList<LessonProgress> mLessonsProgress;

    private Context mAppContext;

    private LessonProgressLab(Context appContext) {
        mAppContext = appContext;
        mLessonsProgress = new ArrayList<>();

        Random random = new Random();
        for(int i = 0; i < StaticData.classes.length; i++) {
            ArrayList<LessonProgress.Mark> marks = new ArrayList<>();

            Calendar calendar = GregorianCalendar.getInstance();
            int numberMarks = random.nextInt(25) + 10;
            for (int j = 0; j < numberMarks; j++) {

                calendar.set(Calendar.MONTH, 1 + random.nextInt(3));
                calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(27) + 1);
                int rat = random.nextInt(5);
                if (rat > 0) {
                    rat++;
                }
                marks.add(LessonProgress.Mark.createMark(rat, calendar.getTime()));
            }
            Collections.sort(marks);
            mLessonsProgress.add(new LessonProgress(StaticData.classes[i], marks));
        }

    }

    public static LessonProgressLab get(Context c) {
        if (sLessonProgressLab == null) {
            sLessonProgressLab = new LessonProgressLab(c);
        }
        return sLessonProgressLab;
    }

    public ArrayList<LessonProgress> getLessonProgress() {
        return mLessonsProgress;
    }
}
