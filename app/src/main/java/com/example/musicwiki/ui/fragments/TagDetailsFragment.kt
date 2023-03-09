package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.musicwiki.adapters.TagItemsViewPagerAdapter
import com.example.musicwiki.databinding.FragmentTagDetailsBinding
import com.example.musicwiki.ui.MainActivity
import com.example.musicwiki.ui.MainViewModel
import com.example.musicwiki.utils.Resource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TagDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTagDetailsBinding
    private lateinit var viewModel : MainViewModel
    private val viewPagerCallback : OnPageChangeCallback by lazy {
        object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupTabLayout()
        viewModel.tagInfo.observe(viewLifecycleOwner){response ->
            when(response) {
                is Resource.Success -> {
                    binding.tvTagName.text = response.data?.tag?.name?.uppercase()
                    binding.tvTagDes.text = response.data?.tag?.wiki?.summary
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(),response.message,Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnBackPress.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

    }

    private fun setupTabLayout() {
        binding.apply {
            tagViewPager.adapter = TagItemsViewPagerAdapter(activity as MainActivity)
            tagViewPager.offscreenPageLimit = 3
            TabLayoutMediator(tagTabLayout, tagViewPager) { tab: TabLayout.Tab, position: Int ->
                when (position) {
                    0 -> {
                        tab.text = "Albums"
                    }
                    1 -> {
                        tab.text = "Artists"
                    }
                    2 -> {
                        tab.text = "Tracks"
                    }
                }
            }.attach()
            tagViewPager.registerOnPageChangeCallback(viewPagerCallback)
        }
    }


}