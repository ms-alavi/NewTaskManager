package com.example.task2.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.task2.controller.State;
import com.example.task2.database.TaskDAO;
import com.example.task2.database.TaskDBSchema;
import com.example.task2.database.TaskRoom;
import com.example.task2.model.Task;
import com.example.task2.model.User;
import com.example.task2.model.UserWithTasks;

import java.util.List;
import java.util.UUID;

public class TaskDBRepository {

    private static TaskDBRepository sTaskDBRepository;
    // private SQLiteDatabase mDatabase;
    private TaskDAO mTaskDatabaseDAO;
    private Context mContext;

    public static TaskDBRepository getInstance(Context context) {
        if (sTaskDBRepository == null)
            sTaskDBRepository = new TaskDBRepository(context);
        return sTaskDBRepository;
    }

    private TaskDBRepository(Context context) {
        mContext = context.getApplicationContext();
      /*  TaskDBHelper taskDBHelper = new TaskDBHelper(mContext);
        mDatabase = taskDBHelper.getWritableDatabase();*/
        TaskRoom db = Room.databaseBuilder(mContext, TaskRoom.class, TaskDBSchema.NAME)
                .allowMainThreadQueries()
                .build();
        mTaskDatabaseDAO = db.getTaskDatabaseDao();

    }

    //**************************************************************
    //select * from taskTable ;
    public List<Task> getTasks() {
      /*  List<TaskEntity> tasks = new ArrayList<>();
        Cursor cursor = mDatabase.query(TaskDBSchema.TaskTable.NAME,
                null, null, null,
                null, null, null);
        if (cursor == null || cursor.getCount() == 0) return tasks;

        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                TaskEntity task = extractTaskFromCursor(cursor);
                tasks.add(task);
            }

        } finally {
            cursor.close();
        }
        return tasks;*/
        return mTaskDatabaseDAO.getTasks();
    }
    public List<Task> getTasksForUserForState(State state,Long id){
        return mTaskDatabaseDAO.getTasksForStateForUser(state,id);
    }

    public List<User> getUsers() {
        return mTaskDatabaseDAO.getUsers();
    }

  /*  public List<Task> getTasksForState(State state) {
     *//*   List<TaskEntity> tasks = new ArrayList<>();
        String where = Cols.STATE + "=?";
        String[] whereArgs = new String[]{state.name()};
        Cursor cursor = mDatabase.query(TaskDBSchema.TaskTable.NAME,
                null, where, whereArgs,
                null, null, null);
        if (cursor == null || cursor.getCount() == 0) return tasks;

        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                TaskEntity task = extractTaskFromCursor(cursor);
                tasks.add(task);
            }
        } finally {
            cursor.close();
        }
        return tasks;*//*

    }*/
    //select * from taskTable where uuid = id;

    public Task getTask(UUID uuid) {
     /*   String where = Cols.UUID + "=? ";
        String[] WhereArgs = new String[]{uuid.toString()};

        Cursor cursor = mDatabase.query(TaskDBSchema.TaskTable.NAME,
                null, where, WhereArgs,
                null, null, null);
        if (cursor == null || cursor.getCount() == 0)
            return null;
        try {
            cursor.moveToFirst();
            TaskEntity task = extractTaskFromCursor(cursor);
            return task;

        } finally {
            cursor.close();
        }*/
        return mTaskDatabaseDAO.getTask(uuid);
    }

    public User getUser(Long id) {
        return mTaskDatabaseDAO.getUser(id);
    }
    public List<User> getUserWithPass(String pass){
        return mTaskDatabaseDAO.getUserWithPass(pass);
    }

    public List<User> getUserWithPassAndUser(String pass, String userName) {
        return mTaskDatabaseDAO.getUserWithPassAndUser (pass,userName);
    }


    public void insertTask(Task task) {
     /*   ContentValues value = getContentValues(task);
        mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, value);*/
        mTaskDatabaseDAO.insertTask(task);
    }

    public void insertTasks(Task... tasks) {
        mTaskDatabaseDAO.insertTasks();
    }

    public void insertUser(User user) {
        mTaskDatabaseDAO.insertUser(user);
    }

    public void insertUsers(User... users) {
        mTaskDatabaseDAO.insertUsers(users);
    }


    public void updateTask(Task task) {
    /*    ContentValues values = getContentValues(task);
        //concat is a dangerous operator for using in whereClause statement we use whereArgs
        //instead of concat to pass id
        String whereClause = Cols.UUID + "= ?";
        String[] whereArgs = new String[]{task.getId().toString()};
        mDatabase.update(TaskDBSchema.TaskTable.NAME, values, whereClause, whereArgs);*/
        mTaskDatabaseDAO.updateTask(task);
    }

    public void updateUser(User user) {
        mTaskDatabaseDAO.insertUser(user);
    }

    public void updateUsers(User... users) {
        mTaskDatabaseDAO.insertUsers(users);
    }

    public void deleteTask(Task task) {
        /*String whereClause = Cols.UUID + "= ?";
        String[] whereArgs = new String[]{task.getId().toString()};
        mDatabase.delete(TaskDBSchema.TaskTable.NAME, whereClause, whereArgs);*/
        mTaskDatabaseDAO.deleteTask(task);
    }

    public void deleteUser(User user) {
        mTaskDatabaseDAO.deleteUser(user);
    }

    public List<Task> searchTask(String string) {
        return mTaskDatabaseDAO.searchTask(string);
    }

    public List<User> searchUser(String string) {
        return mTaskDatabaseDAO.searchUser(string);
    }
    public List<UserWithTasks> getUserWithTasks(){
        return mTaskDatabaseDAO.getUsersWithTasks();
    }

    public List<UserWithTasks> getUserWithTasksForStates(State state) {
        return mTaskDatabaseDAO.getUsersWithTasksForStates(state);
    }

  /*  @NotNull
    private ContentValues getContentValues(TaskEntity task) {
        ContentValues value = new ContentValues();
        value.put(Cols.UUID, task.getId().toString());
        value.put(Cols.DATE, task.getDate().toString());
        value.put(Cols.DESCRIPTION, task.getDescription());
        //*****************************get name of enum value(return a string value for enum)
        value.put(Cols.STATE, task.getState());
        value.put(Cols.TITLE, task.getTitle());

        return value;
    }*/
/*

    @NotNull
    private TaskEntity extractTaskFromCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(Cols.UUID)));
        String title = cursor.getString(cursor.getColumnIndex(Cols.TITLE));
        String description = cursor.getString(cursor.getColumnIndex(Cols.DESCRIPTION));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(Cols.DATE)));
        String state = cursor.getString(cursor.getColumnIndex(Cols.STATE));

        return new TaskEntity(title, description, State.valueOf(state), date, uuid,time);
    }
*/

}
