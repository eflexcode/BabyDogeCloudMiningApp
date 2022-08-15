package com.app.babydogecloudminingapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MINING_COUNT_TABLE")
data class MiningCount(
    @PrimaryKey val key : Int,
    @ColumnInfo(name="balance") val balance : Long,
    @ColumnInfo(name="currently_mining") val currentlyMining : Long,
    @ColumnInfo(name="time_left") val timeLeft : Long
) {


}