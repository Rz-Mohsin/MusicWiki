package com.example.musicwiki.models.tagTopArtistsResponseModels

import com.example.musicwiki.models.Image
import com.example.musicwiki.models.tagTopAlbumsResponseModels.AlbumTrackAttr

data class ArtistX(
    val attr: AlbumTrackAttr,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)