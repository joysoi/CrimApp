package com.example.nikola.soccerjar.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ListItemsDao {

    @Query("SELECT * FROM ListItems")
    LiveData<List<ListItems>> getListOfData();

    @Query("SELECT * FROM ListItems WHERE id = :id")
    LiveData<List<ListItems>> getListItemsById(int[] id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ListItems> wholeList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllById(int[] id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItems(ListItems listItemsInsert);

}
