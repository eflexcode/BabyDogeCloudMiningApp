package com.app.babydogecloudminingapp.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.babydogecloudminingapp.ui.MainActivity

@Database(entities = [MiningCount::class], version = 1)
abstract class MiningCountDatabase : RoomDatabase() {

   abstract fun miningDao(): MiningCountDao

    companion object {

        private var DB: MiningCountDatabase? = null

        fun getDB(context: Context): MiningCountDatabase? {
            if (DB == null) {
                DB = Room.databaseBuilder(
                    context.applicationContext,
                    MiningCountDatabase::class.java,
                    "Mining.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB
        }

    }


    fun firstInsert() {

        val miningCount = MiningCount(1,1000000000,0,0)

      InsertASync(miningDao()).execute(miningCount) // miningDao().insert(miningCount)
    }


    class InsertASync(mining: MiningCountDao) : AsyncTask<MiningCount, Void, Void>() {

        var mMining: MiningCountDao = mining

        override fun doInBackground(vararg params: MiningCount?): Void? {

            params[0]?.let { mMining.insert(it) }

            return null
        }

    }
}