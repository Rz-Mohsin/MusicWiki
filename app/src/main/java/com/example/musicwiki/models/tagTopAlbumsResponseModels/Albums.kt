package com.example.musicwiki.models.tagTopAlbumsResponseModels

data class Albums(
    val attr: TopAlbumsAttr,
    val album: List<Album>
)