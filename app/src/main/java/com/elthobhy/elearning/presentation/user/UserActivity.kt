package com.elthobhy.elearning.presentation.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}