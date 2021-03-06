package com.github.ahmadriza.stockbit.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.ahmadriza.stockbit.R
import com.github.ahmadriza.stockbit.utils.data.autoCleared

/**
 * Created on 11/27/20.
 */

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), IBaseView {

    protected var binding: T by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        view.findViewById<View>(R.id.btn_back)
            ?.setOnClickListener { findNavController().navigateUp() }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObservers()
        initData()
    }
}