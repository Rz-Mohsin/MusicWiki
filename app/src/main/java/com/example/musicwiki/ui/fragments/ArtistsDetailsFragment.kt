package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.adapters.AlbumAdapter
import com.example.musicwiki.adapters.GenreAdapter
import com.example.musicwiki.adapters.TrackAdapter
import com.example.musicwiki.databinding.FragmentArtistsDetailBinding
import com.example.musicwiki.models.CustomAlbum
import com.example.musicwiki.models.CustomTrack
import com.example.musicwiki.ui.MainActivity
import com.example.musicwiki.ui.MainViewModel
import com.example.musicwiki.utils.Resource

class ArtistsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentArtistsDetailBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var topAlbumsAdapter: AlbumAdapter
    private lateinit var topTrackAdapter: TrackAdapter
    private lateinit var tagAdapter: GenreAdapter
    private val artistTagList = ArrayList<String>()
    private val artistAlbumList = ArrayList<CustomAlbum>()
    private val artistTrackList = ArrayList<CustomTrack>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistsDetailBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        tagAdapter = GenreAdapter { tag ->
            viewModel.getTagInfo(tag)
            (activity as MainActivity).openFragment(TagDetailsFragment::class.java)
        }
        topAlbumsAdapter = AlbumAdapter(requireActivity()){
            viewModel.getAlbumInfo(it.artistName,it.name)
            (activity as MainActivity).openFragment(AlbumDetailsFragment::class.java)
        }
        topTrackAdapter = TrackAdapter(requireActivity()) {

        }
        observeViewModel()
        binding.btnBackPress.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.artistInfo.observe(viewLifecycleOwner){response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { data ->
                        var imageUrl : String? = null
                        if(data.artist.image?.isNotEmpty() == true) {
                            imageUrl = data.artist.image.lastOrNull()?.url
                        }
                        imageUrl?.let { url ->
                            Glide.with(requireActivity())
                                .load(url)
                                .timeout(30000) // Set a 30-second timeout
                                .placeholder(R.drawable.default_image) // Set a placeholder image
                                .into(binding.ivArtistImage)
                        }
                        val fetchedTags = data.artist.tags.tag
                        for(tag in fetchedTags) {
                            artistTagList.add(tag.name)
                        }
                        binding.apply {
                            tvArtistName.text = data.artist.name
                            playCountValue.text = formatCount(data.artist.stats?.playcount)
                            followersValue.text = formatCount(data.artist.stats?.listeners)
                            data.artist.bio?.summary?.let {
                                tvArtistDes.text = it
                            }
                            rvArtistTagList.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
                            rvArtistTagList.adapter = tagAdapter
                            tagAdapter.differ.submitList(artistTagList)
                        }
                        tagAdapter.notifyDataSetChanged()
                        binding.progressArtistDetails.isVisible = false
                    }
                }
                is Resource.Loading -> {
                    binding.progressArtistDetails.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressArtistDetails.isVisible = false
                }
            }
        }

        viewModel.artistTopAlbums.observe(viewLifecycleOwner){response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { data ->
                        val fetchedAlbumList = data.topalbums?.album
                        fetchedAlbumList?.let {
                            for(album in it) {
                                artistAlbumList.add(CustomAlbum(album.artist.name,album.image,album.name))
                            }
                        }
                        binding.apply {
                            rvTopAlbumsList.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
                            rvTopAlbumsList.adapter = topAlbumsAdapter
                            topAlbumsAdapter.differ.submitList(artistAlbumList)
                        }
                        topAlbumsAdapter.notifyDataSetChanged()
                        binding.progressTopAlbums.isVisible = false
                    }
                }
                is Resource.Loading -> {
                    binding.progressTopAlbums.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressTopAlbums.isVisible = false
                }
            }
        }
        viewModel.artistTopTracks.observe(viewLifecycleOwner){response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { data ->
                        val fetchedTrackList = data.toptracks?.track
                        fetchedTrackList?.let {
                            for(track in it){
                                artistTrackList.add(CustomTrack(track.artist.name,track.image,track.name))
                            }
                        }
                        binding.apply {
                            rvTrackList.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
                            rvTrackList.adapter = topTrackAdapter
                            topTrackAdapter.differ.submitList(artistTrackList)
                        }
                        topTrackAdapter.notifyDataSetChanged()
                        binding.progressTopTracks.isVisible = false
                    }
                }
                is Resource.Loading -> {
                    binding.progressTopTracks.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressTopTracks.isVisible = false
                }
            }

        }
    }

    private fun formatCount(countStr: String?): String {
        countStr?.let { it ->
            val count = it.toLongOrNull() ?: return it
            return when {
                count >= 1000000 -> "${count / 1000000}M"
                count >= 1000 -> "${count / 1000}.${(count % 1000) / 100}k"
                else -> count.toString()
            }
        }
        return ""
    }
}