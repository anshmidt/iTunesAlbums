package com.anshmidt.itunesalbums.network.models

import com.anshmidt.itunesalbums.network.TrackResponseKeys
import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName(TrackResponseKeys.KIND)
    val kind: String,

    @SerializedName(TrackResponseKeys.TRACK_NAME)
    val name: String
)