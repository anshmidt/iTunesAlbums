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
import com.anshmidt.itunesalbums.view.adapters.TracksListAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album_info.*
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject


class AlbumInfoActivity : AppCompatActivity(), AlbumInfoViewPresenterContract.View {

    @Inject
    lateinit var presenter: AlbumInfoPresenter

    private val tracksListAdapter =
        TracksListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_info)
        hideTitleFromAppBar()
        initDagger()
        receiveAlbumFromIntent()
        presenter.onViewCreated()
        setupTracksListAdapter()
    }

    private fun initDagger() {
        ItunesAlbumsApplication.component
            .plus(AlbumInfoMvpModule(this))
            .inject(this)
    }

    private fun hideTitleFromAppBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun showAlbumInfo(album: Album) {
        textArtistNameAlbumInfo.text = album.artistName
        textAlbumNameAlbumInfo.text = album.albumName
        showAlbumArtwork(album)
        showPrice(album)
        showGenre(album)
    }

    private fun showAlbumArtwork(album: Album) {
        Glide.with(this)
            .load(album.largeArtworkUrl)
            // displaying a small low-resolution artwork while the large artwork is downloading
            .thumbnail(Glide.with(this)
                .load(album.smallArtworkUrl))
            .centerCrop()
            .skipMemoryCache(false)  // caching of the images is enabled
            .placeholder(R.drawable.album_artwork_placeholder)
            .error(R.drawable.album_artwork_placeholder)
            .fallback(R.drawable.album_artwork_placeholder)
            .into(imageAlbumArtworkAlbumInfo)
    }

    private fun showPrice(album: Album) {
        if (album.currencyCode == null || album.price == null) {
            return
        }
        val currencySymbol = getCurrencySymbol(album.currencyCode)
        textPriceAlbumInfo.text = getString(R.string.price_album_info, currencySymbol, album.price)
    }

    private fun showGenre(album: Album) {
        album.primaryGenreName?.let {
            textGenreAlbumInfo.text = it
        }
    }

    /**
     * Returns currency symbol if currency was recognized, and currency code if currency not recognized.
     */
    private fun getCurrencySymbol(currencyCode: String): String {
        return try {
            val currency = Currency.getInstance(currencyCode)
            currency.symbol
        } catch (e: IllegalArgumentException) {
            currencyCode
        }
    }

    private fun receiveAlbumFromIntent() {
        val album: Album = intent.getParcelableExtra(KEY_INTENT_ALBUM)!!
        presenter.setAlbumInfo(album)
    }

    private fun setupTracksListAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewTracksList.layoutManager = linearLayoutManager
        // Disable scrolling on the tracks list - because the whole album info view is scrolled.
        recyclerViewTracksList.setNestedScrollingEnabled(false)
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