package com.anshmidt.itunesalbums.mvp.contracts

import com.anshmidt.itunesalbums.network.models.Album

interface MainViewPresenterContract {

    interface View {
        fun displayAlbums(albums: List<Album>)
        fun showServerNotAvailableErrorMessage(error: Throwable)
        fun openAlbumInfoActivity(album: Album)
    }

    interface Presenter {
        fun onViewCreated()
        fun onViewStopped()
        fun onViewDestroyed()

        fun onSearchRequest(searchQuery: String)
        fun onSearchViewExpand()
        fun onSearchViewCollapse()

        fun onAlbumClick(position: Int, album: Album)
    }

}
