package com.example.musicwiki.models.tagTopArtistsResponseModels

data class TopArtistsAttr(
    val page: String,
    val perPage: String,
    val tag: String,
    val total: String,
    val totalPages: String
)