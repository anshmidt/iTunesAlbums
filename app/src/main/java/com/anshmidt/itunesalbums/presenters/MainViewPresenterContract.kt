package com.anshmidt.itunesalbums.presenters

import com.anshmidt.itunesalbums.network.models.Album
import java.util.Date

interface MainViewPresenterContract {

    interface View {
        fun displayAlbums(albums: List<Album>)
        fun showServerNotAvailableErrorMessage(error: Throwable)
    }

    interface Presenter {
        fun onViewCreated()
        fun onViewStopped()
        fun onViewDestroyed()
        fun onSearchRequest(searchQuery: String)

        fun onSearchViewExpand()
        fun onSearchViewCollapse()
    }

}
