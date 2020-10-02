package com.example.task2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.task2.database.TaskDBSchema.TaskTable.Cols;

import androidx.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {
    public TaskDBHelper(@Nullable Context context) {
        super(context, TaskDBSchema.NAME, null, TaskDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableTask(db);

    }

    private void createTableTask(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TaskDBSchema.TaskTable.NAME + "(");
        sb.append(Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append(Cols.UUID + " TEXT NOT NULL,");
        sb.append(Cols.TITLE + "TEXT ,");
        sb.append(Cols.DESCRIPTION + "TEXT,");
        sb.append(Cols.STATE + " TEXT NOT NULL,");
        sb.append(Cols.DATE + "TEXT");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
