package com.anshmidt.itunesalbums.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anshmidt.itunesalbums.ItunesAlbumsApplication
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.di.component.DaggerApplicationComponent
import com.anshmidt.itunesalbums.di.module.AlbumInfoMvpModule
import com.anshmidt.itunesalbums.mvp.contracts.AlbumInfoViewPresenterContract
import com.anshmidt.itunesalbums.mvp.presenters.AlbumInfoPresenter
import com.anshmidt.itunesalbums.network.models.Album
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
    }

    private fun provideAlbumToPresenter() {
        val artistName = intent.getStringExtra(KEY_INTENT_ARTIST_NAME)!!
        val albumName = intent.getStringExtra(KEY_INTENT_ALBUM_NAME)!!
        val artworkUrl = intent.getStringExtra(KEY_INTENT_ARTWORK_URL)!!

        val album = Album(artistName = artistName, albumName = albumName, artworkUrl = artworkUrl)
        presenter.setAlbumInfo(album)
    }
}