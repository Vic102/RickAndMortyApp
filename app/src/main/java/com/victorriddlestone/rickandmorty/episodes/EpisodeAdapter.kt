package com.victorriddlestone.rickandmorty.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victorriddlestone.rickandmorty.R

class EpisodeAdapter(private val listener: (Episodes) -> Unit) : ListAdapter<Episodes, EpisodeAdapter.ViewHolder>(EpisodesAdapterDiffUtilCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_episode, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episodes = getItem(position)
        holder.tvName.text = episodes.name
        holder.tvDate.text = episodes.air_date
        holder.tvEpisode.text = episodes.episode

        holder.itemView.setOnClickListener {
            listener(episodes)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName : TextView = view.findViewById(R.id.tv_name)
        val tvDate : TextView = view.findViewById(R.id.tv_date)
        val tvEpisode : TextView = view.findViewById(R.id.tv_episode)
    }
}

class EpisodesAdapterDiffUtilCallBack: DiffUtil.ItemCallback<Episodes>() {
    override fun areItemsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
        return oldItem == newItem
    }
}