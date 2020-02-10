package com.anshmidt.itunesalbums.mvp.presenters

import com.anshmidt.itunesalbums.mvp.contracts.MainViewPresenterContract
import com.anshmidt.itunesalbums.network.ItunesApi
import com.anshmidt.itunesalbums.network.RequestValues
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.network.models.ItunesAlbumsResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class MainPresenter @Inject constructor(val view: MainViewPresenterContract.View, val itunesApi: ItunesApi)
    : MainViewPresenterContract.Presenter, BasePresenter() {

    val MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT = 100
    val MAX_NUMBER_OF_ALBUMS_FOR_DEFAULT = 10
    private var suggestedAlbums: List<Album>? = null

    override fun onViewCreated() {
        // A list of suggested albums appears when user opens the app
        displaySuggestedAlbums()
    }

    private fun displaySuggestedAlbums() {
        suggestedAlbums?.let {
            view.displayAlbums(suggestedAlbums!!)
            return
        }

        val subscription = getSuggestedAlbumsSingle()
            .subscribe(
                { itunesAlbumsResponse: ItunesAlbumsResponse ->
                    onSuggestedAlbumsReceivedFromServer(itunesAlbumsResponse)
                },
                // error message is not shown if there was a failure with getting suggested albums
                { view.displayAlbums(arrayListOf())
                }
            )
        subscriptions.add(subscription)
    }

    override fun onSearchRequest(searchQuery: String) {
        val subscription = getSearchByAlbumResponseSingle(searchQuery)
            .subscribe(
                { itunesAlbumsResponse: ItunesAlbumsResponse ->
                    onSearchResultsReceivedFromServer(itunesAlbumsResponse)
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
        displaySuggestedAlbums()
    }

    private fun getSearchByAlbumResponseSingle(searchQuery: String): Single<ItunesAlbumsResponse> {
        return itunesApi
            .getSearchResults(
                searchText = searchQuery,
                entityType = RequestValues.ALBUM_ENTITY,
                mediaType = RequestValues.MUSIC_MEDIA,
                attribute = RequestValues.ALBUM_TERM_ATTRIBUTE,
                limit = MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getSuggestedAlbumsSingle(): Single<ItunesAlbumsResponse> {
        val searchQuery = getSearchQueryForSuggestedAlbums()
        return itunesApi
            .getSearchResults(
                searchText = searchQuery,
                entityType = RequestValues.ALBUM_ENTITY,
                mediaType = RequestValues.MUSIC_MEDIA,
                attribute = RequestValues.MIX_TERM_ATTRIBUTE,
                limit = MAX_NUMBER_OF_ALBUMS_FOR_DEFAULT
            )
//            .getSearchResults(
//                searchText = "6",
//                entityType = RequestValues.ALBUM_ENTITY,
//                mediaType = RequestValues.MUSIC_MEDIA,
//                attribute = "mixTerm",
//                limit = MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
//            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getSearchQueryForSuggestedAlbums(): String {
        val value =  Random.nextInt(0,9).toString()
        return value
    }

    private fun onSuggestedAlbumsReceivedFromServer(itunesAlbumsResponse: ItunesAlbumsResponse) {
        val albumsSortedAlphabetically = itunesAlbumsResponse.albumsList.sortedBy { it.albumName }
        /*
         Saving suggested albums so they could be displayed when returning to the view without
         requests to the server
         */
        suggestedAlbums = albumsSortedAlphabetically
        view.displayAlbums(albumsSortedAlphabetically)
    }

    private fun onSearchResultsReceivedFromServer(itunesAlbumsResponse: ItunesAlbumsResponse) {
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