package com.anshmidt.itunesalbums.network.models

import com.anshmidt.itunesalbums.network.TrackResponseKeys
import com.google.gson.annotations.SerializedName

data class Album (

    @SerializedName(TrackResponseKeys.ARTIST_NAME)
    val artistName: String,

    /**
     * Collection name is the same as album name
     */
    @SerializedName(TrackResponseKeys.COLLECTION_NAME)
    val albumName: String

)