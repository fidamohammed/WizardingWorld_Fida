package com.example.wizardingworld_fida.ui.favourites

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.databinding.FragmentCharacterListBinding
import com.example.wizardingworld_fida.databinding.FragmentFavouritesBinding
import com.example.wizardingworld_fida.ui.characterList.CharacterListViewModel
import com.example.wizardingworld_fida.util.ClickHandler
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(),ClickHandler {

    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(this).get(CharacterListViewModel::class.java)
        binding = FragmentCharacterListBinding.inflate(inflater)
        recyclerView = binding.rvSchoolList
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getFavoritesFromDb()
        viewModel.favoriteCharacters.observe(viewLifecycleOwner){
            characterAdapter = FavoritesAdapter(requireContext(),it, this)
            recyclerView.adapter = characterAdapter
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val character = characterAdapter.characterList[position]
                viewModel.deleteFromFavorite(character)
                Snackbar.make(requireView(), "Removed from favorites", Snackbar.LENGTH_SHORT).show()
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }


        return binding.root
    }

    override fun clickedCharacterItem(character: CharacterDetailModel) {
        Log.d("clickedperson", "$character")
        var bundle= Bundle()
        bundle.putSerializable("favorite",character)

        findNavController().navigate(R.id.action_favouritesFragment_to_characterDetailFragment, bundle)
    }


}