package com.anshmidt.itunesalbums.network

import com.anshmidt.itunesalbums.network.models.ItunesAlbumsResponse
import com.anshmidt.itunesalbums.network.models.ItunesTracksResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {


    /**
     * Example: //https://itunes.apple.com/search?term=outlw&entity=album&media=music&attribute=albumTerm&limit=5
     */
    @GET(ApiConstants.SEARCH_ENDPOINT)
    fun getSearchResults(
        @Query(RequestKeys.TERM) searchText: String,
        @Query(RequestKeys.ENTITY) entityType: String,
        @Query(RequestKeys.MEDIA) mediaType: String,
        @Query(RequestKeys.ATTRIBUTE) attribute: String,
        @Query(RequestKeys.LIMIT) limit: Int
    ): Single<ItunesAlbumsResponse>

    /**
     * Example: https://itunes.apple.com/lookup?id=982690853&entity=song&media=music
     * The first item in response list is a collection, all other items are tracks.
     */
    @GET(ApiConstants.SEARCH_ENDPOINT)
    fun getTracksByCollectionId(
        @Query(RequestKeys.COLLECTION_ID) collectionId: Int,
        @Query(RequestKeys.ENTITY) entityType: String,
        @Query(RequestKeys.MEDIA) mediaType: String
    ): Single<ItunesTracksResponse>

}
