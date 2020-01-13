package com.example.gnb

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.gnb.ui.main.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Activity representing the main screens of the application
 */
class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
}
