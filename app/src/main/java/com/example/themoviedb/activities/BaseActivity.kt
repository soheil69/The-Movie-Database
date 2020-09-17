package com.example.themoviedb.activities

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.util.AutoDispose
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var autoDispose: AutoDispose

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        autoDispose.bindTo(lifecycle)
    }

    fun isAllPermissionsGranted(permissions: Array<out String>, grantResults: IntArray): Boolean {
        if (permissions.isEmpty()) return true
        if (grantResults.isEmpty() || grantResults.size != permissions.size) return false
        grantResults.forEach { if (it != PackageManager.PERMISSION_GRANTED) return false }
        return true
    }

    fun isAllPermissionsGranted(permissions: Array<out String>): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(applicationContext, it)
                != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.primaryNavigationFragment
        if (fragment is OnBackPressListener && fragment.onBackPressed()) return
        else super.onBackPressed()
    }

    override fun androidInjector() = dispatchingAndroidInjector
}