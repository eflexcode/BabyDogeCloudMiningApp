package com.app.babydogecloudminingapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.babydogecloudminingapp.repository.AuthRepository

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuthRepository = AuthRepository()

    fun doLoginAuth(email: String, password: String,context: Context) {
        repository.doLoginAuth(email, password,context)
    }

    fun doCreateAccountAuth(email: String, password: String, fullName: String, context: Context) {

        repository.doCreateAccountAuth(email, password, fullName, context)

    }

    fun isSuccess(): MutableLiveData<Boolean> {

        return repository.success

    }

}