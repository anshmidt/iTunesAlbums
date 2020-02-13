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

class MainPresenter @Inject constructor(
    val view: MainViewPresenterContract.View,
    val itunesApi: ItunesApi
) : MainViewPresenterContract.Presenter, BasePresenter() {

    val MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT = 100
    val MAX_NUMBER_OF_ALBUMS_FOR_DEFAULT = 10
    private var suggestedAlbums: List<Album>? = null

    override fun onViewCreated() {
        // A list of suggested albums appears when user opens the app
        displaySuggestedAlbums()
    }

    private fun displaySuggestedAlbums() {
        // Show albums if they are present in memory. If not, download them.
        suggestedAlbums?.let {
            view.displayAlbums(it)
            return
        }

        val subscription = getSuggestedAlbumsSingle()
            .subscribe(
                { itunesAlbumsResponse: ItunesAlbumsResponse ->
                    onSuggestedAlbumsReceivedFromServer(itunesAlbumsResponse)
                },
                /*
                 Error message is not shown if there was a failure with getting suggested albums,
                 because suggested albums are an optional thing (user doesn't ask for them),
                 unlike search results.
                 */
                { view.displayAlbums(arrayListOf())
                }
            )
        subscriptions.add(subscription)
    }

    override fun onSearchRequestSubmitted(searchQuery: String) {
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

    override fun onSearchExpanded() {
        view.displayNoAlbums()
    }

    override fun onSearchCollapsed() {
        displaySuggestedAlbums()
    }

    override fun onSearchFocused() {
        view.displayNoAlbums()
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * iTunes API doesn't provide a way to get a list of some popular albums,
     * but when you search using small random numbers as a search query,
     * the search results seem good enough to use them as suggested albums.
     */
    private fun getSearchQueryForSuggestedAlbums(): String {
        return Random.nextInt(0,9).toString()
    }

    private fun onSuggestedAlbumsReceivedFromServer(itunesAlbumsResponse: ItunesAlbumsResponse) {
        val albumsSortedAlphabetically = itunesAlbumsResponse.albumsList.sortedBy { it.albumName }
        /*
         Saving suggested albums so they could be displayed when returning to the view without
         requesting the server.
         */
        suggestedAlbums = albumsSortedAlphabetically
        view.displayAlbums(albumsSortedAlphabetically)
    }

    private fun onSearchResultsReceivedFromServer(itunesAlbumsResponse: ItunesAlbumsResponse) {
        val albumsSortedAlphabetically = itunesAlbumsResponse.albumsList.sortedBy { it.albumName }
        if (albumsSortedAlphabetically.isEmpty()) {
            view.showCannotFindAlbumsMessage()
        } else {
            view.displayAlbums(albumsSortedAlphabetically)
        }
    }

    private fun onErrorFromServer(error: Throwable) {
        view.showServerNotAvailableErrorMessage(error)
    }

    override fun onAlbumClick(position: Int, album: Album) {
        view.openAlbumInfoActivity(album)
    }
}