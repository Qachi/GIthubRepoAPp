package com.example.ktrecyclerview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ktrecyclerview.R
import com.example.ktrecyclerview.databinding.ItemlistBinding
import com.example.ktrecyclerview.model.Item

class RecyclerAdapter(private val recyclerItemListener: RecyclerItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Item> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecyclerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecyclerViewHolder -> {
                val itemValue = items[position]
                holder.bind(itemValue)

                holder.itemView.setOnClickListener {
                    recyclerItemListener.onItemClick(itemValue, position)
                }
            }
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(dataClass: List<Item>) {
        items = dataClass
        notifyDataSetChanged()
    }
    inner class RecyclerViewHolder(private val binding: ItemlistBinding) : RecyclerView.ViewHolder(binding.root) {
        private val repoName: TextView = binding.repository
        private val authorName: TextView = binding.author
        private val star: TextView = binding.star
        fun bind(item: Item) {
            with(binding) {
                repoName.text = item.name
                authorName.text = item.owner.login
                star.text = item.stargazersCount.toString()

                Glide.with(itemView.context).load(item.owner.avatarUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.loading_user_rofile))
                    .apply(RequestOptions.errorOf(R.drawable.loading_user_rofile))
                    .apply(RequestOptions.centerCropTransform()).into(avatar)
            }
        }
    }
    interface RecyclerItemClickListener {
        fun onItemClick(itemView: Item, position: Int)
    }
}