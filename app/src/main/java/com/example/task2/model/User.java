package com.example.task2.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userTable")
public class User {



    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")private Long mUserNameId;
    @ColumnInfo(name = "userName")private String mUserName;
    @ColumnInfo(name = "password")private String mPassword;


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
    public Long getId() {
        return mUserNameId;
    }

    public void setId(Long id) {
        mUserNameId = id;
    }

    public Long getUserNameId() {
        return mUserNameId;
    }

    public void setUserNameId(Long userNameId) {
        mUserNameId = userNameId;
    }

}
