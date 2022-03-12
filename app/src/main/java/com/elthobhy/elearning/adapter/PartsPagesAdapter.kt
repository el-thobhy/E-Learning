package com.elthobhy.elearning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.elearning.databinding.LayoutMarkdownBinding
import com.elthobhy.elearning.databinding.LayoutYoutubeBinding
import com.elthobhy.elearning.models.PartsPage

class PartsPagesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TYPE_YOUTUBE = 1
        private const val TYPE_MARKDOWN = 2
        private const val YOUTUBE = "youtube"
    }

    var partsPage = mutableListOf<PartsPage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MarkDownViewHolder(markdownBinding: LayoutMarkdownBinding)
        : RecyclerView.ViewHolder(markdownBinding.root) {

    }

    class YoutubeViewHolder(ytbinding: LayoutYoutubeBinding)
        : RecyclerView.ViewHolder(ytbinding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_YOUTUBE->{
                val youtubeBinding = LayoutYoutubeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                YoutubeViewHolder(youtubeBinding)
            }
            else->{
                val markdownBinding = LayoutMarkdownBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MarkDownViewHolder(markdownBinding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(partsPage[position].type){
            YOUTUBE -> TYPE_YOUTUBE
            else -> TYPE_MARKDOWN
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

