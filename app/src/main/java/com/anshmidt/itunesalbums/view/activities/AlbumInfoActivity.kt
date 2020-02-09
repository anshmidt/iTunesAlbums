package com.anshmidt.itunesalbums.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshmidt.itunesalbums.ItunesAlbumsApplication
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.di.module.AlbumInfoMvpModule
import com.anshmidt.itunesalbums.mvp.contracts.AlbumInfoViewPresenterContract
import com.anshmidt.itunesalbums.mvp.presenters.AlbumInfoPresenter
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.network.models.Track
import com.anshmidt.itunesalbums.view.TracksListAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album_info.*
import kotlinx.android.synthetic.main.albums_list.*
import javax.inject.Inject

class AlbumInfoActivity : AppCompatActivity(), AlbumInfoViewPresenterContract.View {

    @Inject
    lateinit var presenter: AlbumInfoPresenter

    private val tracksListAdapter = TracksListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_info)
        initDagger()
        provideAlbumToPresenter()
        presenter.onViewCreated()
        setupTracksListAdapter()
    }

    private fun initDagger() {
        ItunesAlbumsApplication.component
            .plus(AlbumInfoMvpModule(this))
            .inject(this)

    }

    override fun showAlbumInfo(album: Album) {
        textArtistNameAlbumInfo.text = album.artistName
        textAlbumNameAlbumInfo.text = album.albumName
        Glide.with(this)
            .load(album.largeArtworkUrl)
            .centerCrop()
            .skipMemoryCache(false)  // caching of the images is enabled
            .placeholder(R.drawable.album_artwork_placeholder)
            .error(R.drawable.album_artwork_placeholder)
            .fallback(R.drawable.album_artwork_placeholder)
            .into(imageAlbumArtworkAlbumInfo)
    }

    private fun provideAlbumToPresenter() {
        val artistName = intent.getStringExtra(KEY_INTENT_ARTIST_NAME)!!
        val albumName = intent.getStringExtra(KEY_INTENT_ALBUM_NAME)!!
        val smallArtworkUrl = intent.getStringExtra(KEY_INTENT_SMALL_ARTWORK_URL) ?: null
        val collectionId = intent.getIntExtra(KEY_INTENT_COLLECTION_ID, 0)

        val album = Album(artistName = artistName, albumName = albumName, smallArtworkUrl = smallArtworkUrl,
            collectionId = collectionId)
        presenter.setAlbumInfo(album)
    }

    fun setupTracksListAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewTracksList.layoutManager = linearLayoutManager
        recyclerViewTracksList.adapter = tracksListAdapter
    }

    override fun showTracks(tracks: List<Track>) {
        tracksListAdapter.updateTracks(tracks)
    }

    override fun showCannotGetTracksMessage(error: Throwable) {
        Toast.makeText(this, getString(R.string.cannot_get_tracks_error_message, error.toString()),
            Toast.LENGTH_SHORT).show()
    }
}