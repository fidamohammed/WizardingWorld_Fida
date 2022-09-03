package com.example.wizardingworld_fida.ui.characterList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.databinding.FragmentCharacterListBinding
import com.example.wizardingworld_fida.paging.CharacterPageAdapter
import com.example.wizardingworld_fida.util.ClickHandler
import com.example.wizardingworld_fida.util.Constants.PAGE_SIZE
import com.example.wizardingworld_fida.util.Constants.TOTAL_PAGES
import com.example.wizardingworld_fida.util.Constants.TOTAL_RESULTS
import com.example.wizardingworld_fida.util.UiState
import com.example.wizardingworld_fida.util.checkForInternet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var characterPageAdapter: CharacterPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCharacterListBinding.inflate(inflater)

        val viewModel = ViewModelProvider(this).get(CharacterListViewModel::class.java)

        recyclerView = binding.rvSchoolList
        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            if(checkForInternet(requireContext())){
                viewModel.getListData().collectLatest {
                    characterPageAdapter.submitData(it)
                }
            }
            else{
                Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show()
            }

        }

        characterPageAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("character", it)
            }
            findNavController().navigate(
                R.id.action_characterListFragment_to_characterDetailFragment,
                bundle
            )
        }

//        if(checkForInternet(requireContext())){
//            viewModel.getCharactersFromApi()
//            viewModel.characters.asLiveData().observe(viewLifecycleOwner){ state->
//
//                when(state){
//                    is UiState.Loading -> {
//
//                    }
//                    is UiState.Error -> {
//
//                        Log.d("Error","Error -> ${state.error}")
//                       // recyclerView.adapter = CharacterAdapter(requireContext(), arrayListOf(),this)
//                    }
//                    is UiState.Success<*> -> {
//                        charactersAdapter.differ.submitList(state.schoolResponse as ArrayList<CharacterItemModel>)
////                        recyclerView.adapter = CharacterAdapter(requireContext(),state.schoolResponse as ArrayList<CharacterItemModel> /* = java.util.ArrayList<com.example.wizardingworld_fida.data.model.CharacterItemModel> */,
////                            this)
//                        val totalPages = TOTAL_RESULTS / PAGE_SIZE + 2
//                        isLastPage = viewModel.characterPage== totalPages
//                    }
//                }
//            }
//        }
//        else{
//            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
//        }


        return binding.root
    }
    private fun setupRecyclerView() {
        characterPageAdapter = CharacterPageAdapter()
        recyclerView.apply {
            adapter = characterPageAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

//
//    override fun clickedCharacterItem(character: CharacterItemModel) {
//        Log.d("clickedperson", "$character")
//        var bundle= Bundle()
//        bundle.putSerializable("character",character)
//
//        findNavController().navigate(R.id.action_characterListFragment_to_characterDetailFragment, bundle)
//    }

}