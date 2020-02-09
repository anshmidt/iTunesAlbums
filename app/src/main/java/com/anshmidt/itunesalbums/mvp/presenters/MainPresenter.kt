package com.anshmidt.itunesalbums.mvp.presenters

import com.anshmidt.itunesalbums.mvp.contracts.MainViewPresenterContract
import com.anshmidt.itunesalbums.network.ItunesApi
import com.anshmidt.itunesalbums.network.RequestValues
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.network.models.ItunesResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val view: MainViewPresenterContract.View)
    : MainViewPresenterContract.Presenter {

    @Inject
    lateinit var itunesApi: ItunesApi

    val MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT_LIMIT = 10
    private var subscriptions = CompositeDisposable()

    override fun onViewCreated() {
        val testAlbums =
            arrayListOf(
                Album("Artist1", "collection1"),
                Album("Artist2", "collection2"),
                Album("Artist3", "collection3")
            )
        view.displayAlbums(testAlbums)
    }

    override fun onViewStopped() {
        if (!subscriptions.isDisposed) {
            subscriptions.clear()
        }
    }

    override fun onViewDestroyed() {
        if (!subscriptions.isDisposed) {
            subscriptions.dispose()
        }
    }

    override fun onSearchRequest(searchQuery: String) {
        val subscription = getSearchByAlbumResponseSingle(searchQuery)
            .subscribe(
                { itunesResponse: ItunesResponse ->
                    onResponseFromServer(itunesResponse)
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
        val testAlbums =
            arrayListOf(
                Album("Artist1", "collection1"),
                Album("Artist2", "collection2"),
                Album("Artist3", "collection3")
            )
        view.displayAlbums(testAlbums)
    }

    private fun getSearchByAlbumResponseSingle(searchQuery: String): Single<ItunesResponse> {
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

    private fun onResponseFromServer(itunesResponse: ItunesResponse) {
        view.displayAlbums(itunesResponse.albumsList)
    }

    private fun onErrorFromServer(error: Throwable) {
        view.showServerNotAvailableErrorMessage(error)
    }

    override fun onAlbumClick(position: Int, album: Album) {

    }
}