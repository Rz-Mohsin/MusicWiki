package com.example.musicwiki.api

import com.example.musicwiki.models.*
import com.example.musicwiki.models.albumInfoResponseModels.AlbumInfoResponse
import com.example.musicwiki.models.artistInfoResponseModels.ArtistInfoResponse
import com.example.musicwiki.models.artistTopAlbumResponseModels.ArtistTopAlbumResponse
import com.example.musicwiki.models.artistTopTrackResponseModels.ArtistTopTrackResponse
import com.example.musicwiki.models.tagTopAlbumsResponseModels.TagTopAlbumsResponse
import com.example.musicwiki.models.tagInfoResponseModels.TagInfoResponse
import com.example.musicwiki.models.tagTopArtistsResponseModels.TagTopArtistsResponse
import com.example.musicwiki.models.tagTopTracksResponseModels.TagTopTracksResponse
import com.example.musicwiki.models.topTagResponseModels.TopTagResponse
import com.example.musicwiki.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    @GET("2.0/")
    suspend fun getTopGenre(
        @Query("method")
        method : String = "tag.getTopTags",
        @Query("api_key")
        api_key : String = API_KEY,
        @Query("format")
        format : String = "json"
    ): Response<TopTagResponse>

    @GET("2.0/")
    suspend fun getTagTopAlbums(
        @Query("method")
        method: String = "tag.gettopalbums",
        @Query("tag")
        tag : String = "disco",
        @Query("api_key")
        api_key : String = API_KEY,
        @Query("format")
        format : String = "json"
    ) : Response<TagTopAlbumsResponse>

    @GET("2.0/")
    suspend fun getTagTopTracks(
        @Query("method")
        method: String = "tag.gettoptracks",
        @Query("tag")
        tag : String = "disco",
        @Query("api_key")
        api_key : String = API_KEY,
        @Query("format")
        format : String = "json"
    ) : Response<TagTopTracksResponse>

    @GET("2.0/")
    suspend fun getTagTopArtists(
        @Query("method")
        method: String = "tag.gettopartists",
        @Query("tag")
        tag : String = "disco",
        @Query("api_key")
        api_key : String = API_KEY,
        @Query("format")
        format : String = "json"
    ) : Response<TagTopArtistsResponse>

    @GET("2.0/")
    suspend fun getTagInfo(
        @Query("method")
        method: String = "tag.getinfo",
        @Query("tag")
        tag : String = "disco",
        @Query("api_key")
        api_key : String = API_KEY,
        @Query("format")
        format : String = "json"
    ) : Response<TagInfoResponse>

    @GET("2.0/")
    suspend fun getAlbumInfo(
        @Query("method")
        method: String = "album.getinfo",
        @Query("api_key")
        api_key : String = API_KEY,
        @Query("artist")
        artist : String = "Cher",
        @Query("album")
        album : String = "Believe",
        @Query("format")
        format : String = "json"
    ) : Response<AlbumInfoResponse>

    @GET("2.0/")
    suspend fun getArtistInfo(
        @Query("format")
        format : String = "json",
        @Query("method")
        method: String = "artist.getinfo",
        @Query("artist")
        artist : String = "Cher",
        @Query("api_key")
        api_key : String = API_KEY,
    ) : Response<ArtistInfoResponse>

    @GET("2.0/")
    suspend fun getArtistTopAlbums(
        @Query("format")
        format : String = "json",
        @Query("method")
        method: String = "artist.gettopalbums",
        @Query("artist")
        artist : String = "cher",
        @Query("api_key")
        api_key : String = API_KEY,
    ) : Response<ArtistTopAlbumResponse>

    @GET("2.0/")
    suspend fun getArtistTopTracks(
        @Query("format")
        format : String = "json",
        @Query("method")
        method: String = "artist.gettoptracks",
        @Query("artist")
        artist : String = "cher",
        @Query("api_key")
        api_key : String = API_KEY,
    ) : Response<ArtistTopTrackResponse>

}