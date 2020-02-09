package com.anshmidt.itunesalbums.mvp.presenters

import android.util.Log
import com.anshmidt.itunesalbums.mvp.contracts.AlbumInfoViewPresenterContract
import com.anshmidt.itunesalbums.network.ApiConstants
import com.anshmidt.itunesalbums.network.ItunesApi
import com.anshmidt.itunesalbums.network.RequestValues
import com.anshmidt.itunesalbums.network.TrackResponseValues
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.network.models.ItunesTracksResponse
import com.anshmidt.itunesalbums.network.models.Track
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumInfoPresenter @Inject constructor(private val view: AlbumInfoViewPresenterContract.View, val itunesApi: ItunesApi)
    : AlbumInfoViewPresenterContract.Presenter, BasePresenter() {

    lateinit var album: Album

    override fun setAlbumInfo(album: Album) {
        this.album = album
    }

    override fun onViewCreated() {
        view.showAlbumInfo(album)
        downloadTracks(album.collectionId)
    }

    private fun downloadTracks(collectionId: Int) {
        val subscription = getTracksSingle(collectionId)
            .subscribe(
                { itunesTracksResponse: ItunesTracksResponse ->
                    onResponseFromServer(itunesTracksResponse)
                },
                { error: Throwable ->
                    onErrorFromServer(error)
                }
            )
        subscriptions.add(subscription)
    }

    private fun getTracksSingle(collectionId: Int): Single<ItunesTracksResponse> {
        return itunesApi
            .getTracksByCollectionId(
                collectionId = collectionId,
                entityType = RequestValues.SONG_ENTITY,
                mediaType = RequestValues.MUSIC_MEDIA
            )
//            .getTracksByCollectionId(982690853, "song", "music")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun onResponseFromServer(itunesTracksResponse: ItunesTracksResponse) {
        Log.d("GSON", Gson().toJson(itunesTracksResponse))
        view.showTracks(retrieveTracksFromServerResponse(itunesTracksResponse))
    }

    /**
     * First item in the server response is a collection, so it's needed to be filtered.
     */
    private fun retrieveTracksFromServerResponse(itunesTracksResponse: ItunesTracksResponse): List<Track> {
        return itunesTracksResponse.tracksList.filter { it.kind == TrackResponseValues.SONG_KIND }
    }

    private fun onErrorFromServer(error: Throwable) {
        view.showCannotGetTracksMessage(error)
    }

}