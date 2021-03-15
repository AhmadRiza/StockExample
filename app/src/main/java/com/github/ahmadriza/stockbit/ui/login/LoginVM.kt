package com.github.ahmadriza.stockbit.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.ahmadriza.stockbit.data.repository.MainRepository

class LoginVM @ViewModelInject constructor(repository: MainRepository) : ViewModel(){

    private val _username=  MutableLiveData<String>()
    val loggedIn = Transformations.switchMap(_username){repository.fakeLogin(it)}

    fun login(username: String) {
        _username.value = username
    }
}