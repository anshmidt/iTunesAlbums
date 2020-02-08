package com.anshmidt.itunesalbums.network

import com.anshmidt.itunesalbums.network.models.ItunesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {

    @GET(ApiConstants.SEARCH_ENDPOINT)
    fun getSearchResults(
        @Query(RequestKeys.TERM) searchText: String,
        @Query(RequestKeys.ENTITY) entityType: String,
        @Query(RequestKeys.MEDIA) mediaType: String,
        @Query(RequestKeys.ATTRIBUTE) attribute: String,
        @Query(RequestKeys.LIMIT) limit: Int
    ): Single<ItunesResponse>

}
