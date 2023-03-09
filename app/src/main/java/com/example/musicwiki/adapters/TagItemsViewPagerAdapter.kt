package com.example.musicwiki.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.musicwiki.ui.fragments.ShowTagComponentFragment

class TagItemsViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ShowTagComponentFragment.newInstance("album")
            1 -> ShowTagComponentFragment.newInstance("artist")
            else -> ShowTagComponentFragment.newInstance("track")
        }
    }

}