package com.example.themoviedb.activities.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.themoviedb.R
import com.example.themoviedb.activities.BaseActivity
import com.example.themoviedb.activities.OnBackPressListener

class MainActivity : BaseActivity() {

    private val mainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            findNavController(R.id.main_nav_host_fragment).navigateUp()
    }

    override fun onBackPressed() {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as? NavHostFragment
        navHostFragment?.let {
            val fragment = navHostFragment.childFragmentManager.primaryNavigationFragment
            if (fragment is OnBackPressListener) {
                if (fragment.onBackPressed()) return
            }
        }
        super.onBackPressed()
    }
}