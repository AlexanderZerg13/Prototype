package ru.infocom_s.propotype.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class LessonProgress {
    private UUID mId;
    private String mName;
    private String mTeacher;
    private ArrayList<Mark> mMarks;

    public LessonProgress() {
        mId = UUID.randomUUID();
    }

    public LessonProgress(String name, String teacher, ArrayList<Mark> marks) {
        this();
        mName = name;
        mTeacher = teacher;
        mMarks = marks;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public ArrayList<Mark> getMarks() {
        return mMarks;
    }

    public String getTeacher() {
        return mTeacher;
    }

    public void setTeacher(String teacher) {
        this.mTeacher = teacher;
    }

    public static class Mark implements Comparable<Mark>{
        // 5 - 5; 4 - 4; 3 - 3; 2 - 2; 0 - н
        private int mRat;
        private Date mDate;
        private String mTypeOfLesson;
        private Mark(int rat, Date date, String typeOfLesson) {
            mRat = rat;
            mDate = date;
            mTypeOfLesson = typeOfLesson;
        }

        public int getRat() {
            return mRat;
        }

        public void setRat(int rat) {
            this.mRat = rat;
        }

        public Date getDate() {
            return mDate;
        }

        public void setDate(Date date) {
            this.mDate = date;
        }

        public String getmTypeOfLesson() {
            return mTypeOfLesson;
        }

        public void setmTypeOfLesson(String mTypeOfLesson) {
            this.mTypeOfLesson = mTypeOfLesson;
        }

        public static Mark createMark(int rat, Date date, String typeOfLesson) {
            if (rat < 0 || rat > 5 || rat == 1) {
                throw new IllegalArgumentException("Mark must be 5, 4, 3, 2 or 0");
            }
            return new Mark(rat, date, typeOfLesson);
        }

        @Override
        public int compareTo(Mark another) {
            return getDate().compareTo(another.getDate());
        }
    }
}
