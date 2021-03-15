package com.github.ahmadriza.stockbit.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.github.ahmadriza.stockbit.R
import com.github.ahmadriza.stockbit.databinding.ActivityMainBinding
import com.github.ahmadriza.stockbit.utils.base.BaseActivity
import com.github.ahmadriza.stockbit.utils.gone
import com.github.ahmadriza.stockbit.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    private val vm: MainVM by viewModels()
    private lateinit var navController: NavController

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun initViews() {

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.graph = navController.navInflater.inflate(R.navigation.main_navigation)
        binding.navView.setupWithNavController(navController)

    }

    override fun initObservers() {
    }

    override fun initData() {
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.watchlist -> {
                binding.bottomAppBar.visible()
            }
            else -> {
                binding.bottomAppBar.gone()
            }

        }
    }


    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(this)
    }

}