package com.github.ahmadriza.stockbit.data.local

import com.github.ahmadriza.stockbit.utils.data.Resource
import timber.log.Timber
import java.lang.Exception

abstract class BaseLocalDataSource {

    protected suspend fun <T> execute(query: suspend ()->T): T? {
        try {
           return query()
        }catch (e: Exception){
            Timber.e(e)
        }
        return null
    }

}