package com.example.musicwiki.models.tagTopTracksResponseModels

import com.example.musicwiki.models.Artist
import com.example.musicwiki.models.Image
import com.example.musicwiki.models.Streamable
import com.example.musicwiki.models.tagTopAlbumsResponseModels.AlbumTrackAttr

data class Track(
    val attr: AlbumTrackAttr,
    val artist: Artist,
    val duration: String,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)