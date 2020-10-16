package com.example.task2.database;

import androidx.room.TypeConverter;

import com.example.task2.controller.State;

import java.util.Date;
import java.util.UUID;

public class Converter {
    @TypeConverter
    public static Date longToDate(Long timeStamp) {
        return new Date(timeStamp);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static UUID stringToUUID(String stringUUID) {
        return UUID.fromString(stringUUID);
    }

    @TypeConverter
    public static String UUIDTOString(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static State stringToState(String stringState) {
        return State.valueOf(stringState);
    }

    @TypeConverter
    public static String stateToString(State state) {
        return state.name();
    }
}
