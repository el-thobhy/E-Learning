package com.elthobhy.elearning.presentation.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}