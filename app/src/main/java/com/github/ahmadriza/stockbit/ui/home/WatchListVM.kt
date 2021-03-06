package com.github.ahmadriza.stockbit.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Index
import com.github.ahmadriza.stockbit.data.repository.MainRepository
import com.github.ahmadriza.stockbit.models.CryptoEntity
import com.github.ahmadriza.stockbit.utils.data.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class WatchListVM @ViewModelInject constructor(private val repository: MainRepository): ViewModel() {

    private val _page = MutableLiveData<Int>()
    val cryptos = Transformations.switchMap(_page) { repository.getCryptoList(it) }

    fun loadMore() {
        if(cryptos.value?.status == Resource.Status.LOADING ||
            cryptos.value?.data?.size?: 0 >= repository.getCryptoCount()) return
        val page = _page.value ?: -1
        _page.value = page + 1
    }

    fun refresh() {
        _page.value = 0
    }


    fun subsribeSocketForItem(list: List<CryptoEntity>) = viewModelScope.launch{

        repository.processSocketRequest(list.map { it.subsKey })
    }

}