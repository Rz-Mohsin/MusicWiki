package com.example.musicwiki.models.albumInfoResponseModels

import com.example.musicwiki.models.Artist
import com.example.musicwiki.models.Streamable
import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("@attr") val attr : Attr,
    val artist: Artist,
    val duration: Int,
    val name: String,
    val streamable: Streamable,
    val url: String
)