package com.marufalam.roomdatabasedemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();

   /* @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    *//*@Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);*/

    //Update query
    @Query("UPDATE User SET name = :nameText,number = :numberText WHERE uid = :uID")
    void update(int uID,String nameText,String numberText);

    @Insert
    void insertRecord(User users);

    @Delete
    void delete(User user);
}