package com.example.ecommerce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.ecommerce.Constants.IMAGE_BASE_URL
import com.example.ecommerce.databinding.ItemCategoryBinding
import com.example.ecommerce.model.Category
import com.example.ecommerce.model.CategoryResponse

class CategoryAdapter(val categoryList: List<Category>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater,parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int =categoryList.size

    inner class CategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(categoryItem: Category){
            binding.tvCategoryName.text = categoryItem.categoryName
            Glide.with(binding.root.context)
                .load(IMAGE_BASE_URL + categoryItem.categoryImageUrl)
                .into(binding.ivCategoryImage)
        }
    }
}