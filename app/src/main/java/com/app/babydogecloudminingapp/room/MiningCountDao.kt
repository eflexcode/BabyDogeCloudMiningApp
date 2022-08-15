package com.app.babydogecloudminingapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MiningCountDao {

    @Insert
    fun insert(count: MiningCount)

    @Update
    fun update(count: MiningCount)

    @Query("SELECT * FROM MINING_COUNT_TABLE")
    fun getEverythingLive() : LiveData<List<MiningCount>>

    @Query("SELECT * FROM MINING_COUNT_TABLE")
    fun getEverything() : List<MiningCount>
}