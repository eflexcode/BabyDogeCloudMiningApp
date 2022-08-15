package com.app.babydogecloudminingapp.room

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MiningViewModel(application: Application) : AndroidViewModel(application){

   val mApplication = application

   fun insert(miningRepository: MiningRepository){

//       miningRepository.

   }

   fun getEverythingLive() : LiveData<List<MiningCount>>{

      val miningRepository : MiningRepository = MiningRepository(mApplication)

      return miningRepository.getEverythingLive()

   }

}