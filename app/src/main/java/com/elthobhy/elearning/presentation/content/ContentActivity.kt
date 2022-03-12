package com.elthobhy.elearning.presentation.content

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elthobhy.elearning.adapter.PagesAdapter
import com.elthobhy.elearning.databinding.ActivityContentBinding
import com.elthobhy.elearning.models.Material
import com.elthobhy.elearning.models.Page
import com.elthobhy.elearning.repository.Repository

class ContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBinding
    private lateinit var pagesAdapter: PagesAdapter
    private var currentPosition = 0
    private var materialPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        pagesAdapter = PagesAdapter(this)
        getDataIntent()
        onAction()
    }

    private fun getDataIntent() {
        if(intent != null){
            materialPosition = intent.getIntExtra(EXTRA_POSISTION,0)
            val materials = intent.getParcelableExtra<Material>(EXTRA_MATERIAL)

            binding.tvTitleToolbar.text = materials?.titleMaterial
            materials?.let{
                getDataContent(it)
            }
        }
    }

    private fun getDataContent(it: Material) {
        showLoading()
        val content = it.idMaterial?.let { it1 -> Repository.getContents(this)?.get(it1) }
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
            pagesAdapter.pages = content?.pages as MutableList<Page>

            binding.vpContent.adapter = pagesAdapter
            binding.vpContent.setPagingEnabled(false)
        }, 1200)
    }

    private fun showLoading() {
        binding.swipeContent.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeContent.isRefreshing = false
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
            swipeContent.setOnRefreshListener {
                getDataIntent()
            }
        }
    }
    companion object{
        const val EXTRA_MATERIAL = "extra_material"
        const val EXTRA_POSISTION = "extra_position"
    }
}