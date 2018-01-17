package com.example.nikola.soccerjar.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ListItems.class}, version = 1)
public abstract class ListItemsDataBase extends RoomDatabase {

    public abstract ListItemsDao listItemsDao();

}
