package ru.ermakov.productsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.ermakov.productsapp.R
import ru.ermakov.productsapp.databinding.ItemProductBinding
import ru.ermakov.productsapp.domain.model.Product


class ProductAdapter(
    private val onItemClickListener: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(product = getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(root)
                    .load(product.thumbnail)
                    .placeholder(R.drawable.anim_loading)
                    .into(imageViewThumbnail)
                textViewTitle.text = product.title
                textViewPrice.text = root.resources.getString(
                    R.string.price_with_currency,
                    product.price
                )
                root.setOnClickListener { onItemClickListener(product) }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}