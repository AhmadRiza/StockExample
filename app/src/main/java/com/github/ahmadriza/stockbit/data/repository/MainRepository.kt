package com.github.ahmadriza.stockbit.data.repository

import androidx.lifecycle.liveData
import com.github.ahmadriza.stockbit.data.local.LocalDataSource
import com.github.ahmadriza.stockbit.data.remote.RemoteDataSource
import com.github.ahmadriza.stockbit.utils.data.performLazyGetOperation
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by riza@deliv.co.id on 11/25/20.
 */

class MainRepository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    private var cryptoCount = 0
    fun getCryptoCount() = cryptoCount

    fun getCryptoList(page: Int) = performLazyGetOperation(
        databaseQuery = {
            local.getCryptoList()
        },
        networkCall = {
            remote.getCryptoList(page)
        },
        saveCallResult = {
            if (page == 0) {
                local.clearCache()
            }
            cryptoCount = it.metadata?.count ?: 0
            local.saveCrypto(it.data ?: emptyList())

        }
    )

    fun fakeLogin(username: String)= liveData(Dispatchers.IO){
        local.fakeLogin(username)
        kotlinx.coroutines.delay(1000)
        emit(true)
    }

    fun isLoggedIn() = getUserName() != null
    fun getUserName() = local.getUserName()

}