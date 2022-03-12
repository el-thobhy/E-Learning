package com.elthobhy.elearning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elthobhy.elearning.databinding.ItemMaterialBinding
import com.elthobhy.elearning.models.Material

class MaterialsAdapter:RecyclerView.Adapter<MaterialsAdapter.ViewHolder>(), Filterable {

    private val listener: ((Material, Int)->Unit)? = null
    var materials = mutableListOf<Material>()
        set(value){
            field = value
            materialFilter = value
            notifyDataSetChanged()
        }

    private var materialFilter = mutableListOf<Material>()
    private val filters = object : Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
            var filteredList = mutableListOf<Material>()
            val filterPatterns = p0.toString().trim().lowercase()

            if(filterPatterns.isEmpty()){
                filteredList = materials
            }else{
                for(material in materials){
                    val title = material.titleMaterial?.trim()?.lowercase()

                    if(title?.contains(filterPatterns)==true){
                        filteredList.add(material)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            materialFilter = p1?.values as MutableList<Material>
            notifyDataSetChanged()
        }

    }

    class ViewHolder(private val binding: ItemMaterialBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(material: Material, listener: ((Material, Int) -> Unit)?) {
            Glide.with(itemView)
                .load(material.thumbnailMaterial)
                .placeholder(android.R.color.darker_gray)
                .into(binding.ivMaterial)
            binding.tvTitleMaterial.text = material.titleMaterial


            listener?.let {
                itemView.setOnClickListener {
                    it(material, adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(materialFilter[position], listener)
    }

    override fun getItemCount(): Int = materialFilter.size
    override fun getFilter(): Filter = filters
}