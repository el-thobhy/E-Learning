package com.elthobhy.elearning.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.elthobhy.elearning.databinding.ItemPageBinding
import com.elthobhy.elearning.models.Page

class PagesAdapter(private val context: Context): PagerAdapter() {

    var pages = mutableListOf<Page>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int = pages.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view ==`object``

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemPageBinding.inflate(LayoutInflater.from(context), container, false)

        bindItem(binding, pages[position])
        container.addView(binding.root)
        return binding.root
    }

    private fun bindItem(binding: ItemPageBinding, page: Page) {
        binding.rvPage.setHasFixedSize(true)
//        binding.rvPage.adapter
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)

}