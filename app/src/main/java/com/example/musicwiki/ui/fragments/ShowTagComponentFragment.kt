package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicwiki.adapters.AlbumAdapter
import com.example.musicwiki.adapters.ArtistsAdapter
import com.example.musicwiki.adapters.TrackAdapter
import com.example.musicwiki.databinding.FragmentShowTagComponentBinding
import com.example.musicwiki.models.CustomAlbum
import com.example.musicwiki.models.CustomArtist
import com.example.musicwiki.models.CustomTrack
import com.example.musicwiki.ui.MainActivity
import com.example.musicwiki.ui.MainViewModel
import com.example.musicwiki.utils.Resource

class ShowTagComponentFragment : Fragment() {

    private lateinit var binding: FragmentShowTagComponentBinding
    private lateinit var viewModel: MainViewModel
    private var mode = "album"
    private val tagAlbums = ArrayList<CustomAlbum>()
    private val tagArtists = ArrayList<CustomArtist>()
    private val tagTracks = ArrayList<CustomTrack>()
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var artistAdapter : ArtistsAdapter
    private lateinit var trackAdapter: TrackAdapter

    companion object {
        fun newInstance(mode : String) : ShowTagComponentFragment {
            val fragment = ShowTagComponentFragment()
            val args = Bundle()
            args.putString("mode",mode)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null) mode = requireArguments().getString("mode").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowTagComponentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        albumAdapter = AlbumAdapter(requireActivity()){ selectedAlbum ->
            val artistName = selectedAlbum.artistName
            val albumName = selectedAlbum.name
            viewModel.getAlbumInfo(artistName,albumName)
            (activity as MainActivity).openFragment(AlbumDetailsFragment::class.java)
        }
        artistAdapter = ArtistsAdapter(requireActivity()){ artist ->
            viewModel.getArtistInfo(artist.name)
            viewModel.getArtistTopAlbums(artist.name)
            viewModel.getArtistTopTracks(artist.name)
            (activity as MainActivity).openFragment(ArtistsDetailsFragment::class.java)
        }
        trackAdapter = TrackAdapter(requireActivity()){
            val selectedTrack = it
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        when(mode) {
            "album" -> {
                viewModel.tagTopAlbums.observe(viewLifecycleOwner){ response ->
                    when(response) {
                        is Resource.Success -> {
                            val fetchedList = response.data?.albums?.album
                            fetchedList?.let {
                                for(item in it) {
                                    tagAlbums.add(CustomAlbum(item.artist.name,item.image,item.name))
                                    Log.e("error001","Image list artist: ${item.image}")
                                }
                                binding.apply {
                                    rvTagComponent.layoutManager = GridLayoutManager(requireActivity(),2)
                                    rvTagComponent.adapter = albumAdapter
                                    albumAdapter.differ.submitList(tagAlbums)
                                }
                            }
                            albumAdapter.notifyDataSetChanged()
                            binding.progressBarTagComponent.isVisible = false
                        }
                        is Resource.Loading -> {
                            binding.progressBarTagComponent.isVisible = true
                        }
                        is Resource.Error -> {
                            binding.progressBarTagComponent.isVisible = false
                        }
                    }
                }
            }
            "artist" -> {
                viewModel.tagTopArtist.observe(viewLifecycleOwner){ response ->
                    when(response) {
                        is Resource.Success -> {
                            val fetchedList = response.data?.topartists?.artist
                            fetchedList?.let {
                                for(item in it) {
                                    tagArtists.add(CustomArtist(item.image,item.name))
                                }
                                binding.apply {
                                    rvTagComponent.layoutManager = GridLayoutManager(requireActivity(),2)
                                    rvTagComponent.adapter = artistAdapter
                                    artistAdapter.differ.submitList(tagArtists)
                                }
                            }
                            artistAdapter.notifyDataSetChanged()
                            binding.progressBarTagComponent.isVisible = false
                        }
                        is Resource.Loading -> {
                            binding.progressBarTagComponent.isVisible = true
                        }
                        is Resource.Error -> {
                            binding.progressBarTagComponent.isVisible = false
                        }
                    }
                }
            }
            "track" -> {
                viewModel.tagTopTracks.observe(viewLifecycleOwner){ response ->
                    when(response) {
                        is Resource.Success -> {
                            val fetchedList = response.data?.tracks?.track
                            fetchedList?.let {
                                for(item in it) {
                                    tagTracks.add(CustomTrack(item.artist.name,item.image,item.name))
                                }
                                binding.apply {
                                    rvTagComponent.layoutManager = GridLayoutManager(requireActivity(),2)
                                    rvTagComponent.adapter = trackAdapter
                                    trackAdapter.differ.submitList(tagTracks)
                                }
                            }
                            trackAdapter.notifyDataSetChanged()
                            binding.progressBarTagComponent.isVisible = false
                        }
                        is Resource.Loading -> {
                            binding.progressBarTagComponent.isVisible = true
                        }
                        is Resource.Error -> {
                            binding.progressBarTagComponent.isVisible = false
                        }
                    }
                }
            }
        }
    }
}