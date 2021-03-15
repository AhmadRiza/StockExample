package com.github.ahmadriza.stockbit.utils.base

import com.github.ahmadriza.stockbit.utils.data.Resource
import timber.log.Timber

abstract class BaseLocalDataSource {

    protected suspend fun <T> getResult(call: suspend () -> T?): Resource<T> {
        try {
            val response = call()
            if (response != null) {
                return Resource.success(response)
            }
            return error("null value")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        return Resource.error("Failed to get value cause: $message")
    }

}