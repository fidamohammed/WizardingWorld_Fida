package com.example.wizardingworld_fida.ui.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.databinding.FragmentCharacterDetailBinding
import com.example.wizardingworld_fida.util.UiState
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
        val character = requireArguments().get("character") as CharacterItemModel

        //binding.detailHeader.tvNameCharacterDetail.text = character.name
        val characterDetailViewModel = ViewModelProvider(this).get(CharacterDetailViewModel::class.java)
        characterDetailViewModel.getCharacterDetail(character.id)

        characterDetailViewModel.characterDetail.asLiveData().observe(viewLifecycleOwner){state ->
            when(state){
                is UiState.Loading -> {

                }
                is UiState.Error -> {

                }
                is UiState.Success<*> -> {
                    val character = state.schoolResponse as CharacterDetailModel
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

        }

        return binding.root
    }

}