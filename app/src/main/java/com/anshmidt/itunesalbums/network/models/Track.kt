package com.anshmidt.itunesalbums.network.models

import com.anshmidt.itunesalbums.network.TrackResponseKeys
import com.google.gson.annotations.SerializedName

data class Track (

    @SerializedName(TrackResponseKeys.ARTIST_NAME)
    val artistName: String,

    @SerializedName(TrackResponseKeys.COLLECTION_NAME)
    val collectionName: String,

    @SerializedName(TrackResponseKeys.TRACK_NAME)
    val trackName: String
)