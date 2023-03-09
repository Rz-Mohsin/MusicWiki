package com.example.musicwiki.models.tagTopAlbumsResponseModels

import com.example.musicwiki.models.Artist
import com.example.musicwiki.models.Image
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("@attr") val attr: AlbumTrackAttr,
    val artist: Artist,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val url: String
)