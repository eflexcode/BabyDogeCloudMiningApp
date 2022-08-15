package com.app.babydogecloudminingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountDownViewModel :ViewModel() {

    private val timePassed = MutableLiveData<Long>()

    @JvmName("getTimePassed1")
    fun getTimePassed() : LiveData<Long> {

        return timePassed
    }

}