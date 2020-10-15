package com.example.task2.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithTasks {
    @Embedded
    public User mUser;

    @Relation(
            parentColumn = "userId",
            entityColumn = "userCreatorId"
    )
    public List<Task> mTasks;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }


}
