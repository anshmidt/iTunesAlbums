package com.anshmidt.itunesalbums.network.models

import android.os.Parcelable
import com.anshmidt.itunesalbums.network.AlbumResponseKeys
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Album (

    //----Mandatory fields-------------------------------
    @SerializedName(AlbumResponseKeys.ARTIST_NAME)
    val artistName: String,

    /**
     * Collection name is the same as album name
     */
    @SerializedName(AlbumResponseKeys.COLLECTION_NAME)
    val albumName: String,

    @SerializedName(AlbumResponseKeys.COLLECTION_ID)
    val collectionId: Int,

    //------Optional fields-------------------------------
    @SerializedName(AlbumResponseKeys.ARTWORK_URL_100)
    val smallArtworkUrl: String? = null,

    @SerializedName(AlbumResponseKeys.PRIMARY_GENRE_NAME)
    val genre: String? = null,

    @SerializedName(AlbumResponseKeys.COLLECTION_PRICE)
    val price: Float? = null,

    /**
     * 3-letter code, for example USD, EUR
     */
    @SerializedName(AlbumResponseKeys.CURRENCY)
    val currencyCode: String? = null

) : Parcelable {

    val largeArtworkUrl: String?
        get() = smallArtworkUrl?.let {
            provideLargeArtworkUrl(smallArtworkUrl)
        }

    /**
     * It's possible to get larger artwork by an URL not specified in iTunes server response:
     * For example, response contains 100*100 artwork URL:
     * "artworkUrl100":"<...>/100x100bb.jpg"
     * Then, if you change 100 to 600 in this URL, this will be a valid link to the 600*600 artwork.
     */
    private fun provideLargeArtworkUrl(smallArtworkUrl: String): String {
        return smallArtworkUrl.replace("100x100", "600x600") //if url not contains 100X100, it simply returns smallArtworkUrl
    }


}