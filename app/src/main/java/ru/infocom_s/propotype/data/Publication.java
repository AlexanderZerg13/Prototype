package ru.infocom_s.propotype.data;

import java.util.UUID;

public class Publication {

    private UUID mId;
    private String mAuthor;
    private String mName;

    public Publication() {
        mId = UUID.randomUUID();
    }

    public Publication(String author, String name) {
        this();
        this.mAuthor = author;
        this.mName = name;
    }

    public UUID getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
