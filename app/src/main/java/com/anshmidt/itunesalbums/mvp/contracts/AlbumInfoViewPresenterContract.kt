package com.anshmidt.itunesalbums.mvp.contracts

import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.network.models.Track

interface AlbumInfoViewPresenterContract {

    interface View {
        fun showAlbumInfo(album: Album)
        fun showTracks(tracks: List<Track>)
        fun showCannotGetTracksMessage(error: Throwable)
    }

    interface Presenter {
        fun setAlbumInfo(album: Album)
        fun onViewCreated()
        fun onViewStopped()
        fun onViewDestroyed()
    }

}