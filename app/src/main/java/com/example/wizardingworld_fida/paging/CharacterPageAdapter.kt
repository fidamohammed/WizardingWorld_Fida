package com.example.wizardingworld_fida.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.databinding.CharacterListItemBinding

class CharacterPageAdapter: PagingDataAdapter<CharacterItemModel, CharacterPageAdapter.CharacterViewHolder>(DiffUtilCallback()) {
    override fun onBindViewHolder(holder: CharacterPageAdapter.CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.binding.nameCharacterList.text = character?.name
        Glide.with(holder.itemView).load(character?.imageUrl).apply(
            RequestOptions()
                .placeholder(R.drawable.user)
        )
            .into(holder.binding.ivCharacterList)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterPageAdapter.CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_list_item, parent, false))
    }

    inner class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = CharacterListItemBinding.bind(itemView)

    }
    class DiffUtilCallback : DiffUtil.ItemCallback<CharacterItemModel>(){
        override fun areItemsTheSame(
            oldItem: CharacterItemModel,
            newItem: CharacterItemModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterItemModel,
            newItem: CharacterItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}