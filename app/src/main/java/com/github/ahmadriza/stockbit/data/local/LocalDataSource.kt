package com.github.ahmadriza.stockbit.data.local

import com.github.ahmadriza.stockbit.data.local.db.CryptoDao
import com.github.ahmadriza.stockbit.data.remote.interceptor.AuthInterceptor
import com.github.ahmadriza.stockbit.models.Crypto
import com.github.ahmadriza.stockbit.models.User
import javax.inject.Inject

/**
 * Created by riza@deliv.co.id on 11/25/20.
 */

class LocalDataSource @Inject constructor(
    private val preference: SharedPreferenceHelper,
    private val db: CryptoDao
    ) : BaseLocalDataSource() {

    fun getCryptoList() = db.getCrypto()
    suspend fun saveCrypto(data: List<Crypto>) = execute { db.insertCrypto(data.map { it.asEntity() }) }
    suspend fun clearCache() = execute { db.clearAll() }
    suspend fun updateCrypto(cryptoId: String, price: Double) = execute {
        db.updatePrice(cryptoId.toInt(), price)
    }

    fun fakeLogin(username: String) = preference.saveUser(username)
    fun getUserName() = preference.getUserName()



}