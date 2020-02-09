package com.anshmidt.itunesalbums.network


object ApiConstants {
    const val BASE_URL = "https://itunes.apple.com"
    const val SEARCH_ENDPOINT = "/search"
}

object RequestKeys {
    const val TERM = "term"
    const val ENTITY = "entity"
    const val MEDIA = "media"
    const val ATTRIBUTE = "attribute"
    const val LIMIT = "limit"
    const val COLLECTION_ID = "id"
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

object AlbumResponseKeys {
    const val ARTIST_NAME = "artistName"
    const val COLLECTION_NAME = "collectionName"
    const val ARTWORK_URL_100 = "artworkUrl100"
    const val COLLECTION_ID = "collectionId"
}

object TrackResponseKeys {
    const val KIND = "kind"
    const val TRACK_NAME = "trackName"
}

object TrackResponseValues {
    const val SONG_KIND = "song"
}