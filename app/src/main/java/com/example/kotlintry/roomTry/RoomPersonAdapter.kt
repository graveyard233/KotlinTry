package com.example.kotlintry.roomTry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.kotlintry.R

class RoomPersonAdapter : BaseQuickAdapter<Person,RoomPersonAdapter.PersonVH>() {


    override fun onBindViewHolder(holder: PersonVH, position: Int, item: Person?) {
        item?.run {
            holder.uid.text = uid.toString()
            holder.age.text = age.toString()
            holder.name.text = name
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): PersonVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_room_person,parent,false)
        return PersonVH(view)
    }


    inner class PersonVH(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)
    {
        val uid = itemView.findViewById<TextView>(R.id.item_person_uid)
        val name = itemView.findViewById<TextView>(R.id.item_person_name)
        val age = itemView.findViewById<TextView>(R.id.item_person_age)
    }

}