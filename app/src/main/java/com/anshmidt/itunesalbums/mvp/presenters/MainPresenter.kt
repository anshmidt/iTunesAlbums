package com.anshmidt.itunesalbums.mvp.presenters

import com.anshmidt.itunesalbums.mvp.contracts.MainViewPresenterContract
import com.anshmidt.itunesalbums.network.ItunesApi
import com.anshmidt.itunesalbums.network.RequestValues
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.network.models.ItunesAlbumsResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(val view: MainViewPresenterContract.View, val itunesApi: ItunesApi)
    : MainViewPresenterContract.Presenter, BasePresenter() {

    val MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT_LIMIT = 100

    override fun onViewCreated() {
        val testAlbums: List<Album> = arrayListOf()
        view.displayAlbums(testAlbums)
    }



    override fun onSearchRequest(searchQuery: String) {
        val subscription = getSearchByAlbumResponseSingle(searchQuery)
            .subscribe(
                { itunesAlbumsResponse: ItunesAlbumsResponse ->
                    onResponseFromServer(itunesAlbumsResponse)
                },
                { error: Throwable ->
                    onErrorFromServer(error)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onSearchViewExpand() {
        view.displayAlbums(arrayListOf())
    }

    override fun onSearchViewCollapse() {
        val testAlbums: List<Album> = arrayListOf()
        view.displayAlbums(testAlbums)
    }

    private fun getSearchByAlbumResponseSingle(searchQuery: String): Single<ItunesAlbumsResponse> {
        return itunesApi
            .getSearchResults(
                searchText = searchQuery,
                entityType = RequestValues.ALBUM_ENTITY,
                mediaType = RequestValues.MUSIC_MEDIA,
                attribute = RequestValues.ALBUM_TERM_ATTRIBUTE,
                limit = MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT_LIMIT
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun onResponseFromServer(itunesAlbumsResponse: ItunesAlbumsResponse) {
        val albumsSortedAlphabetically = itunesAlbumsResponse.albumsList.sortedBy { it.albumName }
        view.displayAlbums(albumsSortedAlphabetically)
    }

    private fun onErrorFromServer(error: Throwable) {
        view.showServerNotAvailableErrorMessage(error)
    }

    override fun onAlbumClick(position: Int, album: Album) {
        view.openAlbumInfoActivity(album)
    }
}