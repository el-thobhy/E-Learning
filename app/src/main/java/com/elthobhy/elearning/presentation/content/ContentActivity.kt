package com.elthobhy.elearning.presentation.content

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elthobhy.elearning.adapter.MaterialsAdapter
import com.elthobhy.elearning.databinding.ActivityContentBinding
import com.elthobhy.elearning.repository.Repository

class ContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onAction()
    }
    private fun onAction() {
        binding.apply {
            btnBackContent.setOnClickListener {
                finish()
            }
            btnNextContent.setOnClickListener {
                Toast.makeText(this@ContentActivity,"Next",Toast.LENGTH_LONG).show()
            }
            btnPrevContent.setOnClickListener {
                Toast.makeText(this@ContentActivity,"Prev", Toast.LENGTH_LONG).show()
            }
        }
    }
}