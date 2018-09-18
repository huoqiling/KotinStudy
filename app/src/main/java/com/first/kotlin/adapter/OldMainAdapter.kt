package com.first.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.first.kotlin.R

/**
 * 首页适配器
 */
class OldMainAdapter : RecyclerView.Adapter<OldMainAdapter.MainViewHolder> {

    private var context: Context? = null
    private var list: List<String>? = null
    private var itemClick: ItemClick? = null

    constructor(context: Context, list: List<String>, itemClick: ItemClick) {
        this.context = context
        this.list = list
        this.itemClick = itemClick
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main, null)
        val holder = MainViewHolder(view)
        holder.title.setOnClickListener(onClick())
        return holder
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        try {
            holder.title.text = list?.get(position)
        } catch (e: Exception) {
            e.message
        }
        holder.title.setTag(position)
    }

    override fun getItemCount(): Int {
        return list?.size as Int
    }

    inner class onClick : View.OnClickListener {
        override fun onClick(v: View?) {
            val position: Int = v!!.getTag() as Int
            itemClick!!.OnItemClick(v, position)
        }

    }


    class MainViewHolder : RecyclerView.ViewHolder {

        var title: TextView

        constructor(view: View) : super(view) {
            title = view.findViewById<TextView>(R.id.tvTitle)
        }
    }


    interface ItemClick {
        fun OnItemClick(v: View, position: Int)

    }
}