package com.example.heytaxi;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    public void addUser(User user);

    @Query("SELECT * FROM user")
    public List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE userID=:id LIMIT(1)")
    public User getUserById(int id);
}
