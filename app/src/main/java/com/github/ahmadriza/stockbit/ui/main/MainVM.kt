package com.github.ahmadriza.stockbit.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.github.ahmadriza.stockbit.data.repository.MainRepository

class MainVM @ViewModelInject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun isLoggedIn() = repository.isLoggedIn()

}