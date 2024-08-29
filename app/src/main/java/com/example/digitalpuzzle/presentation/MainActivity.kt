package com.example.digitalpuzzle.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalpuzzle.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            launchWelcomeFragment()
        }
    }

    private fun launchWelcomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, WelcomeFragment.newInstance())
            .commit()
    }
}