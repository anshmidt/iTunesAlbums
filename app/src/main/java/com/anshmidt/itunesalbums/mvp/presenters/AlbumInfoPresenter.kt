package com.anshmidt.itunesalbums.mvp.presenters

import com.anshmidt.itunesalbums.mvp.contracts.AlbumInfoViewPresenterContract
import com.anshmidt.itunesalbums.network.models.Album
import javax.inject.Inject

class AlbumInfoPresenter @Inject constructor(private val view: AlbumInfoViewPresenterContract.View)
    : AlbumInfoViewPresenterContract.Presenter {

    lateinit var album: Album

    override fun setAlbumInfo(album: Album) {
        this.album = album
    }

    override fun onViewCreated() {
        view.showAlbumInfo(album)
    }

    override fun onViewStopped() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}