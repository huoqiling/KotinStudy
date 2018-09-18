package com.first.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.first.kotlin.R
import com.first.kotlin.bean.GridInfo

/**
 * gridView 适配器
 */

class GridAdapter(con: Context?, list: List<GridInfo>?) : RecyclerView.Adapter<GridAdapter.GridHolder>() {

    var context = con
    var gridList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridHolder {
        val view = LayoutInflater.from(context!!).inflate(R.layout.item_grid_view, parent, false)
        val holder = GridHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return gridList!!.size
    }

    override fun onBindViewHolder(holder: GridHolder, position: Int) {
        val gridInfo = gridList!!.get(position)
        try {
            Log.i("gridInfo.url", gridInfo.url)
            Log.i("gridInfo.explain", gridInfo.explain)
            Glide.with(context!!).load(gridInfo.url).apply(RequestOptions().centerCrop().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)).into(holder.ivImage)
        } catch (e: Exception) {
            e.message
        }
        holder.tvExplain.text = gridInfo.explain
    }

    class GridHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView!!.findViewById<ImageView>(R.id.ivImage)
        var tvExplain = itemView!!.findViewById<TextView>(R.id.tvExplain)
    }
}
