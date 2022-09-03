package com.example.wizardingworld_fida.ui.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.databinding.CharacterListItemBinding
import com.example.wizardingworld_fida.util.ClickHandler

class FavoritesAdapter(val context: Context,
                       val characterList : List<CharacterDetailModel>,
                       val clickListener: ClickHandler)
: RecyclerView.Adapter<FavoritesAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(characterItem: View): RecyclerView.ViewHolder(characterItem),View.OnClickListener {
        val binding = CharacterListItemBinding.bind(characterItem)

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentCharacter = characterList[adapterPosition]
            clickListener.clickedCharacterItem(currentCharacter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.nameCharacterList.text = characterList[position].name
        Glide.with(context).load(characterList[position].imageUrl).apply(
            RequestOptions()
                .placeholder(R.drawable.user)
        )
            .into(holder.binding.ivCharacterList)

    }

    override fun getItemCount(): Int = characterList.size
}