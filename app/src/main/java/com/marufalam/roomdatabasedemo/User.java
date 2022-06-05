package com.marufalam.roomdatabasedemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid = 0;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "number")
    public String number;

    @Ignore
    public User() {
    }

    public User(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Ignore
    public User(int uid, String name, String number) {
        this.uid = uid;
        this.name = name;
        this.number = number;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
