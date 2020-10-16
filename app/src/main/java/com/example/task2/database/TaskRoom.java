package com.example.task2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.task2.model.Task;
import com.example.task2.model.User;

@Database(entities = {Task.class, User.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class TaskRoom extends RoomDatabase
{
    public abstract TaskDAO getTaskDatabaseDao();
}
