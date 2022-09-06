package com.example.wizardingworld_fida.ui.characterList


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.databinding.FragmentCharacterListBinding
import com.example.wizardingworld_fida.util.ClickHandler
import com.example.wizardingworld_fida.util.Constants.PAGE_SIZE
import com.example.wizardingworld_fida.util.Constants.TOTAL_RESULTS
import com.example.wizardingworld_fida.util.UiState
import com.example.wizardingworld_fida.util.checkForInternet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment(),ClickHandler {
    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var isLoading = false
        var isLastPage = false
        var isScrolling = false
        binding = FragmentCharacterListBinding.inflate(inflater)

        val viewModel = ViewModelProvider(this).get(CharacterListViewModel::class.java)

        recyclerView = binding.rvSchoolList
       // progressBar = binding.progressBar

        scrollListener = object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1) && dy>0){
                    viewModel.getCharactersFromApi()
                    isScrolling = false
                    progressBar.isVisible = true
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }
        }
        setupRecyclerView()

        if(checkForInternet(requireContext())){
            viewModel.getCharactersFromApi()
            viewModel.characters.asLiveData().observe(viewLifecycleOwner){ state->

                when(state){
                    is UiState.Loading -> {

                    }
                    is UiState.Error -> {

                        Log.d("Error","Error -> ${state.error}")
                        // recyclerView.adapter = CharacterAdapter(requireContext(), arrayListOf(),this)
                    }
                    is UiState.Success<*> -> {
                        progressBar.isVisible = false
                        charactersAdapter.differ.submitList((state.characterResponse as ArrayList<CharacterItemModel>).toList())

//                        recyclerView.adapter = CharacterAdapter(requireContext(),state.schoolResponse as ArrayList<CharacterItemModel> /* = java.util.ArrayList<com.example.wizardingworld_fida.data.model.CharacterItemModel> */,
//                            this)
                        val totalPages = TOTAL_RESULTS / PAGE_SIZE + 2
                        isLastPage = viewModel.characterPage== totalPages
                    }
                }
            }
        }
        else{
            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

    private fun setupRecyclerView() {
        charactersAdapter = CharactersAdapter()
        recyclerView.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(this@CharacterListFragment.scrollListener)
        }
    }
    override fun <T> clickedCharacterItem(character: T) {
        Log.d("clickedperson", "$character")
        var bundle= Bundle()
        bundle.putSerializable("character",character as CharacterItemModel)

        findNavController().navigate(R.id.action_characterListFragment_to_characterDetailFragment, bundle)
    }

}