package com.anshmidt.itunesalbums.network


object ApiConstants {

    const val BASE_URL = "https://itunes.apple.com"
    const val SEARCH_ENDPOINT = "/search"




}

//https://itunes.apple.com/search?term=outlw&entity=album&media=music&attribute=albumTerm&limit=5

object RequestKeys {
    const val TERM = "term"
    const val ENTITY = "entity"
    const val MEDIA = "media"
    const val ATTRIBUTE = "attribute"
    const val LIMIT = "limit"
}

object RequestValues {
    const val ALBUM_ENTITY = "album"
    const val MUSIC_MEDIA = "music"
    const val ALBUM_TERM_ATTRIBUTE = "albumTerm"
}

object ItunesResponseKeys {
    const val RESULT_COUNT = "resultCount"
    const val RESULTS = "results"
}

object TrackResponseKeys {
    const val ARTIST_NAME = "artistName"
    const val COLLECTION_NAME = "collectionName"
    const val TRACK_NAME = "trackName"
}