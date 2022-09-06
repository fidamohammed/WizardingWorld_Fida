package com.example.wizardingworld_fida.ui.characterDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.databinding.FragmentCharacterDetailBinding
import com.example.wizardingworld_fida.util.UiState
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
   private lateinit var binding: FragmentCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater)
        val characterDetailViewModel = ViewModelProvider(this).get(CharacterDetailViewModel::class.java)
        val favoriteButton = binding.addToFavorite

        if(arguments?.containsKey("favorite") == true){
            val character = requireArguments().get("favorite") as CharacterDetailModel
            updateUi(character)
            favoriteButton.setImageResource(R.drawable.favourite)
            //favoriteButton.isVisible = false
            //favoriteButton.hide()
        }
        else{
            val character = requireArguments().get("character") as CharacterItemModel
            characterDetailViewModel.getCharacterDetail(character.id)
            characterDetailViewModel.characterDetail.asLiveData().observe(viewLifecycleOwner){state ->
                when(state){
                    is UiState.Loading -> {
                        CircularProgressIndicator(requireContext())
                    }
                    is UiState.Error -> {
                        Toast.makeText(context,"Oops..! Something went wrong",Toast.LENGTH_SHORT).show()
                        Log.d("error","${state.error}")
                    }
                    is UiState.Success<*> -> {
                        val character = state.characterResponse as CharacterDetailModel
                        characterDetailViewModel.checkIfFavorite(character.id)
                        characterDetailViewModel.favoriteCharacters?.observe(viewLifecycleOwner){
                            if(it != null){
                                favoriteButton.setImageResource(R.drawable.favourite)
                            }
                        }
                        updateUi(character)
                        favoriteButton.setOnClickListener {
                            if(characterDetailViewModel.favoriteCharacters?.value == null){
                                characterDetailViewModel.saveFavoriteToDb(character)
                                favoriteButton.setImageResource(R.drawable.favourite)
                                Snackbar.make(requireView(),"Added to Favorites",Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun updateUi(character: CharacterDetailModel){
        binding.detailHeader.tvNameCharacterDetail.text = character.name
        Glide.with(this).load(character.imageUrl).apply(
            RequestOptions()
                .placeholder(R.drawable.user)
        )
            .into(binding.detailHeader.ivCharacterList)
        binding.detailBody.apply {
            tvBorn.text = character.born?: "No Data Available"
            tvDied.text = character.died?: "No Data Available"
            tvBlood.text = character.blood?: "No Data Available"
            tvBoggart.text = character.boggart?: "No Data Available"
            tvGender.text = character.gender?: "No Data Available"
            tvHouse.text = character.house?.name?: "No Data Available"
        }
    }

}