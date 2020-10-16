package com.example.task2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.task2.controller.State;
import com.example.task2.model.Task;
import com.example.task2.model.User;
import com.example.task2.model.UserWithTasks;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDAO {
    //*************************************task
    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE uuid=:uuid")
    Task getTask(UUID uuid);

    @Query("SELECT * FROM taskTable INNER JOIN userTable ON state =:state and userCreatorId=:id")
    List<Task> getTasksForStateForUser(State state, Long id);

    @Query("SELECT * FROM taskTable JOIN userTable WHERE state=:state")
    List<Task> getTasksForState(State state);

    @Query("SELECT * FROM taskTable WHERE state LIKE :string OR date LIKE :string " +
            "OR time LIKE :string OR title LIKE :string OR description LIKE :string")
    List<Task> searchTask(String string);

    @Insert
    void insertTask(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTasks(Task... tasks);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    //**************************************user
    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE userId=:id")
    User getUser(Long id);

    @Query("SELECT * FROM userTable WHERE  password=:pass")
    List<User> getUserWithPass(String pass);

    @Query("SELECT * FROM userTable WHERE  password=:pass AND userName=:userName")
    List<User> getUserWithPassAndUser(String pass, String userName);

    @Query("SELECT * FROM userTable WHERE password LIKE :string OR userName LIKE :string ")
    List<User> searchUser(String string);

    @Insert
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);

    @Delete
    void deleteUser(User user);

    @Update
    void updateTask(User user);

    //**************************************userWithTasks
    @Transaction
    @Query("SELECT * FROM userTable")
    List<UserWithTasks> getUsersWithTasks();

    @Query("SELECT * FROM userTable join taskTable WHERE userId==userCreatorId and state=:state")
    List<UserWithTasks> getUsersWithTasksForStates(State state);

}
