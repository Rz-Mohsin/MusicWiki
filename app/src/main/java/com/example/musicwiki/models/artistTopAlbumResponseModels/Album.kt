package com.example.musicwiki.models.artistTopAlbumResponseModels

import com.example.musicwiki.models.Artist
import com.example.musicwiki.models.Image

data class Album(
    val artist: Artist,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val playcount: Int,
    val url: String
)