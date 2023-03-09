package com.example.musicwiki.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicwiki.models.albumInfoResponseModels.AlbumInfoResponse
import com.example.musicwiki.models.artistInfoResponseModels.ArtistInfoResponse
import com.example.musicwiki.models.artistTopAlbumResponseModels.ArtistTopAlbumResponse
import com.example.musicwiki.models.artistTopTrackResponseModels.ArtistTopTrackResponse
import com.example.musicwiki.models.tagTopAlbumsResponseModels.TagTopAlbumsResponse
import com.example.musicwiki.models.tagInfoResponseModels.TagInfoResponse
import com.example.musicwiki.models.tagTopArtistsResponseModels.TagTopArtistsResponse
import com.example.musicwiki.models.tagTopTracksResponseModels.TagTopTracksResponse
import com.example.musicwiki.models.topTagResponseModels.TopTagResponse
import com.example.musicwiki.repository.MusicRepository
import com.example.musicwiki.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    val app : Application,
    val musicRepository: MusicRepository
) : AndroidViewModel(app) {

    private val _topGenres = MutableLiveData<Resource<TopTagResponse>>()
    val topGenres : LiveData<Resource<TopTagResponse>>
    get() = _topGenres

    private val _tagInfo = MutableLiveData<Resource<TagInfoResponse>>()
    val tagInfo : LiveData<Resource<TagInfoResponse>>
    get() = _tagInfo

    private val _tagTopAlbums = MutableLiveData<Resource<TagTopAlbumsResponse>>()
    val tagTopAlbums : LiveData<Resource<TagTopAlbumsResponse>>
    get() = _tagTopAlbums

    private val _tagTopTracks = MutableLiveData<Resource<TagTopTracksResponse>>()
    val tagTopTracks : LiveData<Resource<TagTopTracksResponse>>
    get() = _tagTopTracks

    private val _tagTopArtists = MutableLiveData<Resource<TagTopArtistsResponse>>()
    val tagTopArtist : LiveData<Resource<TagTopArtistsResponse>>
    get() = _tagTopArtists

    private val _albumInfo = MutableLiveData<Resource<AlbumInfoResponse>>()
    val albumInfo : LiveData<Resource<AlbumInfoResponse>>
    get() = _albumInfo

    private val _artistInfo = MutableLiveData<Resource<ArtistInfoResponse>>()
    val artistInfo : LiveData<Resource<ArtistInfoResponse>>
    get() = _artistInfo

    private val _artistTopAlbums = MutableLiveData<Resource<ArtistTopAlbumResponse>>()
    val artistTopAlbums : LiveData<Resource<ArtistTopAlbumResponse>>
    get() = _artistTopAlbums

    private val _artistTopTracks = MutableLiveData<Resource<ArtistTopTrackResponse>>()
    val artistTopTracks : LiveData<Resource<ArtistTopTrackResponse>>
    get() = _artistTopTracks

    fun getTopGenre() {
        CoroutineScope(Dispatchers.IO).launch {
            _topGenres.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getTopGenres()
                _topGenres.postValue(handleResponse(response))
            }
            else {
                _topGenres.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getTagInfo(tag : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _tagInfo.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getTagInfo(tag)
                _tagInfo.postValue(handleResponse(response))
            }
            else {
                _tagInfo.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getTagTopAlbums(tag : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _tagTopAlbums.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getTagTopAlbums(tag)
                _tagTopAlbums.postValue(handleResponse(response))
            }
            else {
                _tagTopAlbums.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getTagTopArtists(tag : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _tagTopArtists.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getTagTopArtists(tag)
                _tagTopArtists.postValue(handleResponse(response))
            }
            else {
                _tagTopArtists.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getTagTopTracks(tag : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _tagTopTracks.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getTagTopTracks(tag)
                _tagTopTracks.postValue(handleResponse(response))
            } else {
                _tagTopTracks.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getAlbumInfo(artist : String, album : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _albumInfo.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getAlbumInfo(artist,album)
                _albumInfo.postValue(handleResponse(response))
            } else {
                _albumInfo.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getArtistInfo(artist : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _artistInfo.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getArtistInfo(artist)
                _artistInfo.postValue(handleResponse(response))
            } else {
                _artistInfo.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getArtistTopAlbums(artist : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _artistTopAlbums.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getArtistTopAlbums(artist)
                _artistTopAlbums.postValue(handleResponse(response))
            } else {
                _artistTopAlbums.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun getArtistTopTracks(artist : String) {
        CoroutineScope(Dispatchers.IO).launch {
            _artistTopTracks.postValue(Resource.Loading())
            if(hasInternetConnection()) {
                val response = musicRepository.getArtistTopTracks(artist)
                _artistTopTracks.postValue(handleResponse(response))
            } else {
                _artistTopTracks.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    private fun <T> handleResponse(response: Response<T>) : Resource<T> {
        if(response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean{
        val connectivityManager = app.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else
        {
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}