package ru.infocom_s.propotype;

import java.util.UUID;

public class News {
    private UUID mId;
    private String mTitle;
    private String mDescription;

    public News() {
        mId = UUID.randomUUID();
    }

    public News(String title, String description) {
        this();
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
