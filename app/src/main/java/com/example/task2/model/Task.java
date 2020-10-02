package com.example.task2.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task {

    private String mTitle;
    private String mDescription;
    private State mState;
    private Date mDate;

    private UUID mId;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }




    public UUID getId() {
        return mId;
    }

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID id) {
        mDate = new Date();
        mId = id;
    }

    public Task(String title, String description, State state, Date date, UUID id) {
        mTitle = title;
        mDescription = description;
        mState = state;
        mDate = date;
        mId=id;
    }
}
