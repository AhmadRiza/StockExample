package com.github.ahmadriza.stockbit.ui.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ahmadriza.stockbit.R
import com.github.ahmadriza.stockbit.databinding.FragmentStockbitLoginBinding
import com.github.ahmadriza.stockbit.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.toast

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentStockbitLoginBinding>() {

    override fun getLayoutResource(): Int = R.layout.fragment_stockbit_login
    private val vm: LoginVM by viewModels()

    override fun initViews() {
        binding.btnLogin.setOnClickListener {

            if(binding.etUsernameEmail.text.isNullOrBlank()){
                context?.toast("Email is empty")
                return@setOnClickListener
            }

            if(binding.etPassword.text.isNullOrBlank()){
                context?.toast("Password is empty")
                return@setOnClickListener
            }

            vm.login(binding.etUsernameEmail.text.toString())

        }
    }

    override fun initObservers() {
        vm.loggedIn.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_loginFragment_to_watchlist)
        }
    }

    override fun initData() {
    }
}