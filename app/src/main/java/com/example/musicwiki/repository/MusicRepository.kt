package com.example.musicwiki.repository

import com.example.musicwiki.api.RetrofitInstance

class MusicRepository {
    suspend fun getTopGenres() =
        RetrofitInstance.api.getTopGenre()

    suspend fun getTagTopAlbums(tag : String) =
        RetrofitInstance.api.getTagTopAlbums(tag = tag)

    suspend fun getTagTopTracks(tag : String) =
        RetrofitInstance.api.getTagTopTracks(tag = tag)

    suspend fun getTagTopArtists(tag : String) =
        RetrofitInstance.api.getTagTopArtists(tag = tag)

    suspend fun getTagInfo(tag : String) =
        RetrofitInstance.api.getTagInfo(tag = tag)

    suspend fun getAlbumInfo(artist : String, album : String) =
        RetrofitInstance.api.getAlbumInfo(artist = artist, album = album)

    suspend fun getArtistInfo(artist: String) =
        RetrofitInstance.api.getArtistInfo(artist = artist)

    suspend fun getArtistTopAlbums(artist: String) =
        RetrofitInstance.api.getArtistTopAlbums(artist = artist)

    suspend fun getArtistTopTracks(artist: String) =
        RetrofitInstance.api.getArtistTopTracks(artist = artist)
}