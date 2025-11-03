package com.example.ecommerce.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.ItemCommentBinding
import com.example.ecommerce.model.remote.dto.Review

class ReviewsAdapter(private val reviewList: List<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    // ViewHolder class using ViewBinding
    inner class ReviewViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        with(holder.binding) {
            tvCommentName.text = review.fullName
            tvCommentTitle.text = review.reviewTitle
            tvCommentDescription.text = review.review
            rbComment.rating = review.rating.toFloat()
        }
    }

    override fun getItemCount(): Int = reviewList.size
}
