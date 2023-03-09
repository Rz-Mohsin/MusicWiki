package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.adapters.GenreAdapter
import com.example.musicwiki.databinding.FragmentAlbumDetailsBinding
import com.example.musicwiki.ui.MainActivity
import com.example.musicwiki.ui.MainViewModel
import com.example.musicwiki.utils.Resource

class AlbumDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAlbumDetailsBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var tagAdapter : GenreAdapter
    private val albumTagList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        tagAdapter = GenreAdapter { tag ->
            viewModel.getTagInfo(tag)
            (activity as MainActivity).openFragment(TagDetailsFragment::class.java)
        }
        observeViewModel()
        binding.btnBackPress.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.albumInfo.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let { data ->
                        binding.tvAlbumTitle.text = data.album?.name ?: ""
                        binding.tvArtistName.text = data.album?.artist ?: ""
                        var imageUrl : String? = null
                        if(data.album?.image?.isNotEmpty() == true) {
                            imageUrl = data.album?.image?.last()?.url
                        }
                        imageUrl?.let { url ->
                            Glide.with(requireActivity())
                                .load(url)
                                .timeout(30000) // Set a 30-second timeout
                                .placeholder(R.drawable.default_image) // Set a placeholder image
                                .into(binding.ivAlbumImage)
                        }
                        data.album?.wiki?.summary?.let {
                            binding.tvAlbumDes.text = it
                        }
                        val tagList = data.album?.tags?.tag
                        tagList?.let {
                            for(tag in it) {
                                tag?.name?.let {
                                    albumTagList.add(it)
                                }
                            }
                        }
                        binding.apply {
                            rvAlbumTagList.adapter = tagAdapter
                            rvAlbumTagList.layoutManager = LinearLayoutManager(requireActivity(),
                                HORIZONTAL,false)
                            tagAdapter.differ.submitList(albumTagList)
                            rvAlbumTagList
                        }
                        tagAdapter.notifyDataSetChanged()
                        binding.progressAlbumDetails.isVisible = false
                    }
                }
                is Resource.Loading -> {
                    binding.progressAlbumDetails.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressAlbumDetails.isVisible = false
                    Toast.makeText(requireActivity(),response.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}