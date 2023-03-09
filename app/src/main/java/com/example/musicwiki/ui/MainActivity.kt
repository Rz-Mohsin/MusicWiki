package com.example.musicwiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicwiki.R
import com.example.musicwiki.adapters.GenreAdapter
import com.example.musicwiki.databinding.ActivityMainBinding
import com.example.musicwiki.repository.MusicRepository
import com.example.musicwiki.ui.fragments.TagDetailsFragment
import com.example.musicwiki.utils.Resource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val repository = MusicRepository()
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel : MainViewModel
    private lateinit var adapter : GenreAdapter
    private val listOfTopGenre = ArrayList<String>()
    private val tenGenreList = ArrayList<String>()
    var showInitialGenre = true
    var selectedTag : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,MainViewModelProviderFactory(application,repository))[MainViewModel::class.java]
        adapter = GenreAdapter {
            selectedTag = it
            viewModel.apply {
                getTagInfo(selectedTag!!)
                getTagTopAlbums(selectedTag!!)
                getTagTopArtists(selectedTag!!)
                getTagTopTracks(selectedTag!!)
            }
            openFragment(TagDetailsFragment::class.java)
        }
        viewModel.getTopGenre()

        setUpRecyclerView()
        setUpListener()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.topGenres.observe(this){ response ->
            when(response) {
                is Resource.Success -> {
                    val fetchedList = response.data?.toptags?.tag
                    fetchedList?.let {
                        for(genre in it) {
                            listOfTopGenre.add(genre.name)
                            if(tenGenreList.size<10) {
                                tenGenreList.add(genre.name)
                            }
                        }
                        adapter.differ.submitList(tenGenreList)
                        adapter.notifyDataSetChanged()
                    }
                    binding.progressBar.isVisible = false

                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this,response.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvGenreList.adapter = adapter
        binding.rvGenreList.layoutManager = GridLayoutManager(this,3)
        adapter.differ.submitList(tenGenreList)
    }

    private fun setUpListener() {
        binding.apply {
            btnDropDown.setOnClickListener {
                if(showInitialGenre) {
                    adapter.differ.submitList(listOfTopGenre)
                    binding.btnDropDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    showInitialGenre = false
                } else {
                    adapter.differ.submitList(tenGenreList)
                    binding.btnDropDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    showInitialGenre = true
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun openFragment(fragmentClass: Class<out Fragment>) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragmentClass.newInstance(), null)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}