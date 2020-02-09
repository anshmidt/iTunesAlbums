package com.anshmidt.itunesalbums.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshmidt.itunesalbums.ItunesAlbumsApplication
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.di.component.DaggerApplicationComponent
import com.anshmidt.itunesalbums.di.module.MainMvpModule
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.mvp.presenters.MainPresenter
import com.anshmidt.itunesalbums.mvp.contracts.MainViewPresenterContract
import com.anshmidt.itunesalbums.view.AlbumsListAdapter
import kotlinx.android.synthetic.main.albums_list.*
import javax.inject.Inject

const val APP_PACKAGE_NAME = "com.anshmidt.itunesalbums"
const val KEY_INTENT_ARTIST_NAME = "$APP_PACKAGE_NAME.ARTIST_NAME"
const val KEY_INTENT_ALBUM_NAME = "$APP_PACKAGE_NAME.ALBUM_NAME"
const val KEY_INTENT_ARTWORK_URL = "$APP_PACKAGE_NAME.ARTWORK_URL"

class MainActivity : AppCompatActivity(), MainViewPresenterContract.View, AlbumsListAdapter.AlbumClickListener {

//    private lateinit var linearLayoutManager: LinearLayoutManager
    private val albumsListAdapter = AlbumsListAdapter(arrayListOf() ,this)

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()

        setupAlbumsListAdapter()

        presenter.onViewCreated()
    }

    override fun displayAlbums(albums: List<Album>) {
        albumsListAdapter.updateAlbums(albums)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)


        val searchMenuItem = menu.findItem(R.id.menuItemSearch)
        val searchView = searchMenuItem?.actionView as SearchView

        setSearchViewHint(searchView)


        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                presenter.onSearchViewExpand()
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                presenter.onSearchViewCollapse()
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    presenter.onSearchRequest(it)
                    searchView.clearFocus()
//                    searchView.setQuery("", false)
//                    searchItem.collapseActionView()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onStop() {
        super.onStop()
        presenter.onViewStopped()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    private fun initDagger() {
        ItunesAlbumsApplication.component
            .plus(MainMvpModule(this))
            .inject(this)
    }


    override fun showServerNotAvailableErrorMessage(error: Throwable) {
        Toast.makeText(this, getString(R.string.server_not_available_error_message, error.toString()),
                Toast.LENGTH_SHORT).show()
    }

    private fun setSearchViewHint(searchView: SearchView) {
        searchView.queryHint = getString(R.string.search_field_hint)
    }

    private fun setupAlbumsListAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewAlbumsList.layoutManager = linearLayoutManager
        recyclerViewAlbumsList.adapter = albumsListAdapter
    }

    override fun onAlbumClick(position: Int, album: Album) {
        presenter.onAlbumClick(position, album)
    }

    override fun openAlbumInfoActivity(album: Album) {
        val intent = Intent(this, AlbumInfoActivity::class.java)
        intent.putExtra(KEY_INTENT_ARTIST_NAME, album.artistName)
        intent.putExtra(KEY_INTENT_ALBUM_NAME, album.albumName)
        intent.putExtra(KEY_INTENT_ARTWORK_URL, album.artworkUrl)
        startActivity(intent)
    }
}
