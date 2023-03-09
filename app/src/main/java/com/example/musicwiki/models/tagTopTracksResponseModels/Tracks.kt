package com.example.musicwiki.models.tagTopTracksResponseModels

data class Tracks(
    val attr: TopTrackAttr,
    val track: List<Track>
)