package com.example.wizardingworld_fida.ui.characterList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.databinding.CharacterListItemBinding

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersAdapter.ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_list_item,parent,false))
    }


    val differCallback = object : DiffUtil.ItemCallback<CharacterItemModel>() {
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
    val differ = AsyncListDiffer(this,differCallback)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.binding.nameCharacterList.text = character.name
//        Glide.with(context).load(character.imageUrl).apply(
//            RequestOptions()
//                .placeholder(R.drawable.placeholder)
//        )
//            .into(holder.binding.ivCharacterList)
    }

    override fun getItemCount(): Int =
        differ.currentList.size


    inner class ItemViewHolder(character: View):RecyclerView.ViewHolder(character), View.OnClickListener {
        val binding = CharacterListItemBinding.bind(character)

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            //val currentCharacter = characterList[adapterPosition]
            //clickListener.clickedCharacterItem(currentCharacter)
        }

    }


}