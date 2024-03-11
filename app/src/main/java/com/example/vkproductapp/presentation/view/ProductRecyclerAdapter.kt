package com.example.vkproductapp.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkproductapp.R
import com.example.vkproductapp.data.model.Product
import com.google.android.material.card.MaterialCardView

class ProductRecyclerAdapter: RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView){
        private val productImageView: ImageView = itemView.findViewById(R.id.cardProductImage)
        private val productTitle: TextView = itemView.findViewById(R.id.cardProductTitle)
        private val productDescription: TextView = itemView.findViewById(R.id.cardProductDescription)
        val productCard: MaterialCardView = itemView.findViewById(R.id.cardProduct)
        fun bind(product: Product){
            Glide.with(productImageView)
                .load(product.thumbnail)
                .into(productImageView)
            productTitle.text = product.title
            productDescription.text = product.description
        }
    }

    private val diffUtil: DiffUtil.ItemCallback<Product> = object: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_recycler_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = listDiffer.currentList[position]
        holder.bind(currentProduct)
        holder.productCard.setOnClickListener {
            onItemClickListener?.invoke(currentProduct)
        }
    }

    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((Product) -> Unit)){
        onItemClickListener = clickListener
    }

    fun setData(data: List<Product>){
        val currentList = listDiffer.currentList.toMutableList()
        currentList.addAll(data)
        listDiffer.submitList(currentList)
    }
}