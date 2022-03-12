package com.elthobhy.elearning.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityMainBinding
import com.elthobhy.elearning.presentation.user.UserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            ivUser.setOnClickListener {
                startActivity(Intent(this@MainActivity, UserActivity::class.java))
            }
        }
    }
}