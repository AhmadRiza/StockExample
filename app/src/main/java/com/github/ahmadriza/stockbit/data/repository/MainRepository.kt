package com.github.ahmadriza.stockbit.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.github.ahmadriza.stockbit.data.local.LocalDataSource
import com.github.ahmadriza.stockbit.data.remote.CryptoSocketService
import com.github.ahmadriza.stockbit.data.remote.RemoteDataSource
import com.github.ahmadriza.stockbit.models.SubscribeSocket
import com.github.ahmadriza.stockbit.utils.data.Resource
import com.github.ahmadriza.stockbit.utils.data.performLazyGetOperation
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.filter
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by riza@deliv.co.id on 11/25/20.
 */

class MainRepository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val socket: CryptoSocketService
) {

    private var currentSubsList = emptyList<String>()

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

    fun fakeLogin(username: String) = liveData(Dispatchers.IO) {
//        local.fakeLogin(username)
//        kotlinx.coroutines.delay(1000)
        emit(true)
    }


    fun isLoggedIn() = getUserName() != null
    fun getUserName() = local.getUserName()

    suspend fun processSocketRequest(listSubs: List<String>) {
        val subsMessage = SubscribeSocket(action = "SubAdd", listSubs)
        val unsubsMessage = SubscribeSocket(action = "SubRemove", currentSubsList)

        socket.observeEvents()
            .consumeEach {
                if (it is WebSocket.Event.OnConnectionOpened<*>) {
                    socket.send(unsubsMessage)
                    socket.send(subsMessage)
                    currentSubsList = subsMessage.subs
                }

            }


        socket.observeApiMessage().consumeEach {
            local.updateCrypto(it.from, it.price)
        }


    }


}