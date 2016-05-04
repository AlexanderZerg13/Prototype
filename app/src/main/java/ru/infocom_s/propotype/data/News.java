package ru.infocom_s.propotype.data;

import java.util.UUID;

public class News {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private int mImageViewResource;

    public News() {
        mId = UUID.randomUUID();
    }

    public News(String title, String description, int resource) {
        this();
        mTitle = title;
        mDescription = description;
        mImageViewResource = resource;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImageViewResource() {
        return mImageViewResource;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
