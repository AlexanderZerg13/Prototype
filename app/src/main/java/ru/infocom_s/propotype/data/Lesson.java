package ru.infocom_s.propotype.data;

import java.util.Date;
import java.util.UUID;

public class Lesson {

    private UUID mId;
    private int mNumber;
    private String mName;
    private String mRoom;
    private int mDay;

    public Lesson() {
        mId = UUID.randomUUID();
    }

    public Lesson(int Number, String Name, String Room, int Day) {
        this();
        this.mNumber = Number;
        this.mName = Name;
        this.mRoom = Room;
        this.mDay = Day;
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
}
