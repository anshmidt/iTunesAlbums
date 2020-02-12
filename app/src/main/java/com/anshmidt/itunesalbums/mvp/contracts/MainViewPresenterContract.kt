package com.anshmidt.itunesalbums.mvp.contracts

import com.anshmidt.itunesalbums.network.models.Album

interface MainViewPresenterContract {

    interface View {
        fun displayAlbums(albums: List<Album>)
        fun displayNoAlbums()
        fun openAlbumInfoActivity(album: Album)
        fun showServerNotAvailableErrorMessage(error: Throwable)
        fun showCannotFindAlbumsMessage()
    }

    interface Presenter {
        fun onViewCreated()
        fun onViewStopped()
        fun onViewDestroyed()

        fun onSearchRequestSubmitted(searchQuery: String)
        fun onSearchExpanded()
        fun onSearchCollapsed()
        fun onSearchFocused()

        fun onAlbumClick(position: Int, album: Album)
    }

}
