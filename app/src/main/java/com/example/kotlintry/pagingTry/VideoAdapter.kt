package com.example.kotlintry.pagingTry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlintry.R

class VideoAdapter(private val mContext:Context) : PagingDataAdapter<BilibiliVideo,VideoAdapter.ViewHolder>(COMPARATOR) {
    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<BilibiliVideo>(){
            override fun areItemsTheSame(oldItem: BilibiliVideo, newItem: BilibiliVideo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BilibiliVideo,
                newItem: BilibiliVideo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.item_title)
        val img:ImageView = itemView.findViewById(R.id.item_img)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        if (video != null){
            holder.title.text = video.title
            Glide.with(mContext)
                .load("https:${video.pic}")
                .into(holder.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false)
        return ViewHolder(view)
    }
}