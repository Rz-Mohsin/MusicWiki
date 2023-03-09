package com.example.musicwiki.models.tagTopAlbumsResponseModels

data class TopAlbumsAttr(
    val page: String,
    val perPage: String,
    val tag: String,
    val total: String,
    val totalPages: String
)