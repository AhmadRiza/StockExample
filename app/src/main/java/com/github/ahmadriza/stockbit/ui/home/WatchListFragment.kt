package com.github.ahmadriza.stockbit.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ahmadriza.stockbit.R
import com.github.ahmadriza.stockbit.databinding.FragmentStockbitHomeBinding
import com.github.ahmadriza.stockbit.utils.android.VisibleItemListener
import com.github.ahmadriza.stockbit.utils.base.BaseFragment
import com.github.ahmadriza.stockbit.utils.data.Resource
import com.github.ahmadriza.stockbit.utils.onScrollLoad
import com.github.ahmadriza.stockbit.utils.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WatchListFragment: BaseFragment<FragmentStockbitHomeBinding>() {

    private val vm: WatchListVM by viewModels()
    private val cryptoAdapter by lazy { CryptoAdapter() }

    override fun getLayoutResource(): Int = R.layout.fragment_stockbit_home

    override fun initViews() {
        binding.rvCrypto.adapter = cryptoAdapter
        binding.swipe.setOnRefreshListener { vm.refresh() }

        binding.rvCrypto.onScrollLoad(1){
            vm.loadMore()
        }

        binding.rvCrypto.addOnScrollListener(VisibleItemListener(binding.rvCrypto.layoutManager as LinearLayoutManager){ start, end ->
            vm.subsribeSocketForItem(
                cryptoAdapter.currentList.subList(start, end)
            )
        })
    }

    override fun initObservers() {
        vm.cryptos.observe(viewLifecycleOwner){
            binding.swipe.isRefreshing = it.status == Resource.Status.LOADING
            binding.loading.visibleOrGone(it.status == Resource.Status.LOADING)
            cryptoAdapter.submitList(it.data)
        }
    }

    override fun initData() {
        vm.refresh()
    }
}