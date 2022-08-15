package com.app.babydogecloudminingapp.room

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MiningRepository(context: Context) {

    var miningCountDao : MiningCountDao = MiningCountDatabase.getDB(context)?.miningDao()!!

    fun insert(miningCount: MiningCount) {

        InsertASync(miningCountDao).execute(miningCount)

    }

    fun update(miningCount: MiningCount) {
        UpdateASync(miningCountDao).execute(miningCount)

    }

    fun getEverythingLive(): LiveData<List<MiningCount>> {

        return miningCountDao.getEverythingLive()

    }

    fun getEverything(): List<MiningCount> {

        return miningCountDao.getEverything()

    }

    private class InsertASync(mining: MiningCountDao) : AsyncTask<MiningCount, Void, Void>() {

        private var mMining: MiningCountDao = mining

        override fun doInBackground(vararg params: MiningCount?): Void? {
            params[0]?.let { mMining.insert(it) }
//            params[0]?.let { mining.insert(it) }

            return null
        }

    }

    class UpdateASync(mining: MiningCountDao) : AsyncTask<MiningCount, Void, Void>() {

        private var mMining: MiningCountDao = mining

        override fun doInBackground(vararg params: MiningCount?): Void? {

            params[0]?.let { mMining.update(it) }

            return null
        }

    }

}