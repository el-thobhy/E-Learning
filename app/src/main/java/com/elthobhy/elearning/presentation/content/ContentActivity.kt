package com.elthobhy.elearning.presentation.content

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.elthobhy.elearning.adapter.PagesAdapter
import com.elthobhy.elearning.databinding.ActivityContentBinding
import com.elthobhy.elearning.models.Material
import com.elthobhy.elearning.models.Page
import com.elthobhy.elearning.presentation.main.MainActivity
import com.elthobhy.elearning.repository.Repository
import disable
import enabled
import invisible
import visible

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
        viewPagerCurrentPosition()
    }

    private fun viewPagerCurrentPosition() {
        binding.vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val totalIndex = pagesAdapter.count
                currentPosition = position
                val textIndex = "${currentPosition+1} / $totalIndex"
                binding.tvIndexContent.text = textIndex

                if(currentPosition == 0){
                    binding.btnPrevContent.invisible()
                    binding.btnPrevContent.disable()
                }else{
                    binding.btnPrevContent.visible()
                    binding.btnPrevContent.enabled()
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun getDataIntent() {
        if(intent != null){
            materialPosition = intent.getIntExtra(EXTRA_POSITION,0)
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
            //init untuk tampilan awal index

            val textIndex = "${currentPosition+1} / ${pagesAdapter.count}"
            binding.tvIndexContent.text = textIndex
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
                if(currentPosition < pagesAdapter.count -1){
                    binding.vpContent.currentItem += 1
                }else{
                    val intent = Intent(this@ContentActivity, MainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_POSITION, materialPosition)
                    startActivity(intent)
                    finish()
                }
            }
            btnPrevContent.setOnClickListener {
                binding.vpContent.currentItem -= 1
            }
            swipeContent.setOnRefreshListener {
                getDataIntent()
            }
        }
    }
    companion object{
        const val EXTRA_MATERIAL = "extra_material"
        const val EXTRA_POSITION = "extra_position"
    }
}