package com.anshmidt.itunesalbums.network.models

import com.anshmidt.itunesalbums.network.ItunesResponseKeys
import com.google.gson.annotations.SerializedName

data class ItunesAlbumsResponse (
    @SerializedName(ItunesResponseKeys.RESULT_COUNT)
    val resultCount: Int,

    @SerializedName(ItunesResponseKeys.RESULTS)
    val albumsList: List<Album>
)