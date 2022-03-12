package com.elthobhy.elearning.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.elthobhy.elearning.R
import com.elthobhy.elearning.adapter.MaterialsAdapter
import com.elthobhy.elearning.databinding.ActivityMainBinding
import com.elthobhy.elearning.presentation.user.UserActivity
import com.elthobhy.elearning.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var materialsAdapter: MaterialsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        materialsAdapter = MaterialsAdapter()

        getDataMaterial()

        onAction()
    }

    private fun getDataMaterial() {
        showLoading()
        val materials = Repository.getMaterials(this)
        Log.e("getdata", "getDataMaterial: $materials", )
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
            materials?.let{
                materialsAdapter.materials = it
            }
        },1200)

        binding.rvMaterials.adapter = materialsAdapter
    }
    

    private fun showLoading() {
        binding.swipeMain.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeMain.isRefreshing = false
    }

    private fun onAction() {
        binding.apply {
            ivUser.setOnClickListener {
                startActivity(Intent(this@MainActivity, UserActivity::class.java))
            }
            etSearchMaterial.addTextChangedListener { 
                materialsAdapter.filter.filter(it.toString())
            }
            etSearchMaterial.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    val dataSearch = etSearchMaterial.text.toString().trim()
                    materialsAdapter.filter.filter(dataSearch)
                    return@setOnEditorActionListener  true
                }
                return@setOnEditorActionListener false
            }

            swipeMain.setOnRefreshListener {
                getDataMaterial()
            }
        }
    }
}