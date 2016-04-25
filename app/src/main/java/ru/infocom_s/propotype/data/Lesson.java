package ru.infocom_s.propotype.data;

import java.util.Date;
import java.util.UUID;

public class Lesson {

    private UUID mId;
    private int mNumber;
    private String mName;
    private String mRoom;

    private String mTeacher;
    private int mDay;

    public Lesson() {
        mId = UUID.randomUUID();
    }

    public Lesson(int Number, String Name, String Room, String Teacher, int Day) {
        this();
        this.mNumber = Number;
        this.mName = Name;
        this.mRoom = Room;
        this.mDay = Day;
        this.mTeacher = Teacher;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID Id) {
        this.mId = Id;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int Number) {
        this.mNumber = Number;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String Room) {
        this.mRoom = Room;
    }

    public int getDay() {
        return mDay;
    }

    public void setDate(int Day) {
        this.mDay = Day;
    }

    public String getTeacher() {
        return mTeacher;
    }

    public void setTeacher(String Teacher) {
        this.mTeacher = Teacher;
    }
}
