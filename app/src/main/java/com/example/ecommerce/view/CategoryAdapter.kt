package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.model.remote.Constants.IMAGE_BASE_URL
import com.example.ecommerce.databinding.ItemCategoryBinding
import com.example.ecommerce.model.remote.dto.Category

// callback function is used for handling the click of subcategories
class CategoryAdapter(val categoryList: List<Category>, private val onCategoryClick: (Category) -> Unit   ): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
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

            binding.ivCategoryImage.setOnClickListener {
                onCategoryClick(categoryItem) // invoke callback in Homefragment
                Toast.makeText(binding.root.context,"You've clicked ${categoryItem.categoryName}", Toast.LENGTH_LONG).show()
            }
        }
    }
}