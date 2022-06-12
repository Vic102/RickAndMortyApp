package com.victorriddlestone.rickandmorty.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorriddlestone.rickandmorty.R

class CharacterAdapter(private val listener: (Character) -> Unit) : ListAdapter<Character, CharacterAdapter.ViewHolder>(CharacterAdapterDiffUtilCallBack()){

    //(private val listener: (Character) -> Unit)
    //class CharacterViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
        /*
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
         */

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.tvName.text = character.name
        holder.tvStatus.text = character.status
        holder.tvSpecies.text = character.species
        Glide.with(holder.itemView).load(character.image).into(holder.imAvatar)

        holder.itemView.setOnClickListener {
            listener(character)
        }

/*
        val character = getItem(position)
        holder.binding.tvName.text = character.name
        holder.binding.tvStatus.text = character.status
        holder.binding.tvSpecies.text = character.species
        Glide.with(holder.itemView).load(character.image).into(holder.binding.imAvatar)

        holder.binding.root.setOnClickListener {
            listener(character)
        }
  */
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName : TextView = view.findViewById(R.id.tv_name)
        val tvStatus : TextView = view.findViewById(R.id.tv_status)
        val tvSpecies : TextView = view.findViewById(R.id.tv_species)
        val imAvatar: ImageView = view.findViewById(R.id.im_avatar)
    }


}

class CharacterAdapterDiffUtilCallBack: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}

