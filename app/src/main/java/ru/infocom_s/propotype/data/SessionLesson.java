package ru.infocom_s.propotype.data;

import java.util.Date;
import java.util.UUID;

public class SessionLesson {

    private UUID mId;
    private String mName;
    private Date mDate;
    private String mRoom;
    private String mTeacher;

    public SessionLesson() {
        mId = UUID.randomUUID();
    }

    public SessionLesson(String name, Date date, String room, String teacher) {
        this();
        this.mName = name;
        this.mDate = date;
        this.mRoom = room;
        this.mTeacher = teacher;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        this.mRoom = room;
    }

    public String getTeacher() {
        return mTeacher;
    }

    public void setTeacher(String teacher) {
        this.mTeacher = teacher;
    }
}
