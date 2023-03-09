package com.example.musicwiki.models.artistTopAlbumResponseModels

import com.google.gson.annotations.SerializedName

data class Topalbums(
    @SerializedName("@attr") val attr: Attr,
    val album: List<Album>
)