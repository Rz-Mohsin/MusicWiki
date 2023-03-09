package com.example.musicwiki.models.tagTopArtistsResponseModels

data class TopArtists(
    val attr: TopArtistsAttr,
    val artist: List<ArtistX>
)