package com.example.heytaxi;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 2)
public abstract class HTDatabase extends RoomDatabase {
    private static HTDatabase INSTANCE;
    public abstract UserDAO userDAO();

    public static HTDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, HTDatabase.class, "taxi_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        }
        return INSTANCE;
    }
}
