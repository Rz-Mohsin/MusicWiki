package com.example.musicwiki.models.tagTopTracksResponseModels

data class TopTrackAttr(
    val page: String,
    val perPage: String,
    val tag: String,
    val total: String,
    val totalPages: String
)