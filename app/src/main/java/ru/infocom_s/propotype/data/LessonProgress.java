package ru.infocom_s.propotype.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class LessonProgress {
    private UUID mId;
    private String mName;
    private ArrayList<Mark> mMarks;

    public LessonProgress() {
        mId = UUID.randomUUID();
    }

    public LessonProgress(String name, ArrayList<Mark> marks) {
        this();
        mName = name;
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

    public void setMarks(ArrayList<Mark> marks) {
        this.mMarks = marks;
    }

    public static class Mark implements Comparable<Mark>{
        // 5 - 5; 4 - 4; 3 - 3; 2 - 2; 0 - н
        private int mRat;
        private Date mDate;
        private Mark(int rat, Date date) {
            mRat = rat;
            mDate = date;
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

        public static Mark createMark(int rat, Date date) {
            if (rat < 0 || rat > 5 || rat == 1) {
                throw new IllegalArgumentException("Mark must be 5, 4, 3, 2 or 0");
            }
            return new Mark(rat, date);
        }

        @Override
        public int compareTo(Mark another) {
            return getDate().compareTo(another.getDate());
        }
    }
}
