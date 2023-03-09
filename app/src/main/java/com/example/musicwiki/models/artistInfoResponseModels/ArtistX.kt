package com.example.musicwiki.models.artistInfoResponseModels

import com.example.musicwiki.models.Image

data class ArtistX(
    val image: List<Image>,
    val name: String,
    val url: String
)