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
import com.anshmidt.itunesalbums.di.module.MainMvpModule
import com.anshmidt.itunesalbums.mvp.contracts.MainViewPresenterContract
import com.anshmidt.itunesalbums.mvp.presenters.MainPresenter
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.view.adapters.AlbumsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


const val APP_PACKAGE_NAME = "com.anshmidt.itunesalbums"
const val KEY_INTENT_ALBUM = "$APP_PACKAGE_NAME.ALBUM"


class MainActivity : AppCompatActivity(), MainViewPresenterContract.View, AlbumsListAdapter.AlbumClickListener {

    private val albumsListAdapter =
        AlbumsListAdapter(this)

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
        scrollAlbumsListToTop()
    }

    override fun displayNoAlbums() {
        displayAlbums(arrayListOf())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchMenuItem = menu.findItem(R.id.menuItemSearch)
        val searchView = searchMenuItem?.actionView as SearchView
        setSearchViewHint(searchView)

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                presenter.onSearchExpanded()
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                presenter.onSearchCollapsed()
                return true
            }
        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    presenter.onSearchRequestSubmitted(it)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                presenter.onSearchFocused()
            }
        }

        searchView.maxWidth = Integer.MAX_VALUE  // make searchView fill the whole bar

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
        Toast.makeText(this, getString(R.string.server_not_available_error_message),
                Toast.LENGTH_SHORT).show()
    }

    override fun showCannotFindAlbumsMessage() {
        Toast.makeText(this, getString(R.string.cannot_find_albums_message),
                Toast.LENGTH_LONG).show()
    }

    private fun setSearchViewHint(searchView: SearchView) {
        searchView.queryHint = getString(R.string.search_field_hint)
    }

    private fun setupAlbumsListAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewAlbumsList.layoutManager = linearLayoutManager
        recyclerViewAlbumsList.adapter = albumsListAdapter
    }

    private fun scrollAlbumsListToTop() {
        recyclerViewAlbumsList.layoutManager?.scrollToPosition(0)
    }

    override fun onAlbumClick(position: Int, album: Album) {
        presenter.onAlbumClick(position, album)
    }

    override fun openAlbumInfoActivity(album: Album) {
        val intent = Intent(this, AlbumInfoActivity::class.java)
        intent.putExtra(KEY_INTENT_ALBUM, album)
        startActivity(intent)
    }


}
