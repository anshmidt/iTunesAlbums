package com.anshmidt.itunesalbums.mvp.contracts

import com.anshmidt.itunesalbums.network.models.Album

interface AlbumInfoViewPresenterContract {

    interface View {
        fun showAlbumInfo(album: Album)
    }

    interface Presenter {
        fun setAlbumInfo(album: Album)
        fun onViewCreated()
        fun onViewStopped()
    }

}