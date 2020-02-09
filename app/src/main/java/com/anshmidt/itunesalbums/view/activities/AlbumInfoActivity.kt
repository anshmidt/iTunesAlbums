package com.anshmidt.itunesalbums.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anshmidt.itunesalbums.ItunesAlbumsApplication
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.di.module.AlbumInfoMvpModule
import com.anshmidt.itunesalbums.mvp.contracts.AlbumInfoViewPresenterContract
import com.anshmidt.itunesalbums.mvp.presenters.AlbumInfoPresenter
import com.anshmidt.itunesalbums.network.models.Album
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album_info.*
import javax.inject.Inject

class AlbumInfoActivity : AppCompatActivity(), AlbumInfoViewPresenterContract.View {

    @Inject
    lateinit var presenter: AlbumInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_info)
        initDagger()
        provideAlbumToPresenter()
        presenter.onViewCreated()
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
        val largeArtworkUrl = intent.getStringExtra(KEY_INTENT_LARGE_ARTWORK_URL) ?: null

        val album = Album(artistName = artistName, albumName = albumName, smallArtworkUrl = smallArtworkUrl)
        album.largeArtworkUrl = largeArtworkUrl
        presenter.setAlbumInfo(album)
    }
}