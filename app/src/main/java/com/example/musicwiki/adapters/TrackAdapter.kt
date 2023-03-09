package com.example.musicwiki.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.databinding.ItemTagComponentListBinding
import com.example.musicwiki.models.CustomTrack

class TrackAdapter(
    val context: Context,
    val listener : (CustomTrack) -> Unit
) : RecyclerView.Adapter<TrackAdapter.CustomTrackViewHolder>() {
    inner class CustomTrackViewHolder(val binding: ItemTagComponentListBinding) : ViewHolder(binding.root) {
        fun bind(item : CustomTrack) {
            binding.apply {
                tvTitle.text = item.name
                tvArtist.text = item.artistName
                var imageUrl : String? = null
                if(item.image.isNotEmpty()) {
                    imageUrl = item.image.last().url
                }
                imageUrl?.let { url ->
                    Glide.with(context)
                        .load(url)
                        .timeout(30000) // Set a 30-second timeout
                        .placeholder(R.drawable.default_image) // Set a placeholder image
                        .into(ivLogo)
                }
            }
            binding.root.setOnClickListener {
                listener(item)
            }
        }
    }

    private val differCallback = object : ItemCallback<CustomTrack>() {
        override fun areItemsTheSame(oldItem: CustomTrack, newItem: CustomTrack): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CustomTrack, newItem: CustomTrack): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomTrackViewHolder {
        val view = ItemTagComponentListBinding.inflate(LayoutInflater.from(parent.context))
        return CustomTrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomTrackViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}