package com.anshmidt.itunesalbums.network.models

import com.anshmidt.itunesalbums.network.ItunesResponseKeys
import com.google.gson.annotations.SerializedName

data class ItunesTracksResponse(
    @SerializedName(ItunesResponseKeys.RESULT_COUNT)
    val resultCount: Int,

    @SerializedName(ItunesResponseKeys.RESULTS)
    val tracksList: List<Track>
)