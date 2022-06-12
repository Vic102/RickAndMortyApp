package com.victorriddlestone.rickandmorty.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victorriddlestone.rickandmorty.R

class LocationAdapter(private val listener: (Location) -> Unit) : ListAdapter<Location, LocationAdapter.ViewHolder>(LocationAdapterDiffUtilCallBack())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view : View = layoutInflater.inflate(R.layout.item_location,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = getItem(position)
        holder.tvName.text = location.name
        holder.tvType.text = location.type
        holder.tvDimension.text = location.dimension

        holder.itemView.setOnClickListener {
            listener(location)
        }
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvName : TextView = view.findViewById(R.id.tv_location_name)
        val tvType : TextView = view.findViewById(R.id.tv_location_type)
        val tvDimension : TextView = view.findViewById(R.id.tv_location_dimension)
    }
}




class LocationAdapterDiffUtilCallBack: DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

}