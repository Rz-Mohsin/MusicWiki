package com.example.musicwiki.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.databinding.ItemTagComponentListBinding
import com.example.musicwiki.models.CustomAlbum

class AlbumAdapter(
    val context: Context,
    val listener : (CustomAlbum) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(val binding: ItemTagComponentListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CustomAlbum) {
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

    private val differCallback = object : DiffUtil.ItemCallback<CustomAlbum>() {
        override fun areItemsTheSame(oldItem: CustomAlbum, newItem: CustomAlbum): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CustomAlbum, newItem: CustomAlbum): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder{
        val view = ItemTagComponentListBinding.inflate(LayoutInflater.from(parent.context))
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}