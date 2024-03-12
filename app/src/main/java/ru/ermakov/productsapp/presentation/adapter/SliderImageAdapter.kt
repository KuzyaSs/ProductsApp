package ru.ermakov.productsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.ermakov.productsapp.R
import ru.ermakov.productsapp.databinding.ItemImageBinding

class SliderImageAdapter
    : ListAdapter<String, SliderImageAdapter.SliderImageViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderImageViewHolder {
        return SliderImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderImageViewHolder, position: Int) {
        holder.bind(image = getItem(position))
    }

    inner class SliderImageViewHolder(
        private val binding: ItemImageBinding
    ) : ViewHolder(binding.root) {
        fun bind(image: String) {
            binding.apply {
                Glide.with(root)
                    .load(image)
                    .placeholder(R.drawable.anim_loading)
                    .into(imageViewImage)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}