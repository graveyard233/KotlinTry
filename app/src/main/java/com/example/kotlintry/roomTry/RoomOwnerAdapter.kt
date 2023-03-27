package com.example.kotlintry.roomTry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.kotlintry.GlideApp
import com.example.kotlintry.R

class RoomOwnerAdapter : BaseQuickAdapter<Owner,RoomOwnerAdapter.VH>() {


    override fun onBindViewHolder(holder: VH, position: Int, item: Owner?) {
        item?.run {
            GlideApp.with(holder.faceImg)
                .load(face)
                .placeholder(R.drawable.ic_code)
                .error(R.drawable.ic_launcher_background)
                .into(holder.faceImg)

            holder.mid.text = mid.toString()
            holder.name.text = name
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.item_room_owner,parent,false))
    }

    inner class VH(itemView :View):RecyclerView.ViewHolder(itemView) {
        val faceImg = itemView.findViewById<ImageView>(R.id.item_owner_face)
        val name = itemView.findViewById<TextView>(R.id.item_owner_name)
        val mid = itemView.findViewById<TextView>(R.id.item_owner_mid)
    }
}