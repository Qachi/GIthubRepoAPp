package com.example.ktrecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.ktrecyclerview.R
import com.example.ktrecyclerview.model.Item
import kotlinx.android.synthetic.main.itemlist.view.*
import java.util.*

class RecyclerAdapter(private val recyclerItemListener: RecyclerItemClickListner) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
        )
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

    fun submitList(dataClass: List<Item>) {
        items = dataClass
        notifyDataSetChanged()

    }

    inner class RecyclerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val repoName: TextView = itemView.repository
        private val authorName: TextView = itemView.author
        private val star: TextView = itemView.star


        fun bind(item: Item) {
            repoName.text = item.name
            authorName.text = item.owner?.login
            star.text = item.stargazersCount.toString()

            Glide.with(itemView.context).load(item.owner?.avatarUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.loading_user_rofile))
                .apply(RequestOptions.errorOf(R.drawable.loading_user_rofile))
                .apply(RequestOptions.centerCropTransform()).into(itemView.avatar)
        }
    }

    interface RecyclerItemClickListner {
        fun onItemClick(itemView: Item, position: Int)
    }

}