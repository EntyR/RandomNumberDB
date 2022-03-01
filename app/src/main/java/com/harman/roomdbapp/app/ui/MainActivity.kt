package com.harman.roomdbapp.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harman.roomdbapp.R
import com.harman.roomdbapp.app.ui.fragments.RandomNumbersList
import com.harman.roomdbapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        if (savedInstanceState == null){
            val fragment = RandomNumbersList.newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
        }



        setContentView(binding.root)
    }
}