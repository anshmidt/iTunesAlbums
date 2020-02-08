package com.anshmidt.itunesalbums.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.di.component.DaggerAppComponent
import com.anshmidt.itunesalbums.di.module.MvpModule
import com.anshmidt.itunesalbums.di.module.NetworkModule
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.presenters.MainPresenter
import com.anshmidt.itunesalbums.presenters.MainViewPresenterContract
import com.anshmidt.itunesalbums.view.AlbumsListAdapter
import kotlinx.android.synthetic.main.albums_list.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainViewPresenterContract.View {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val albumsListAdapter = AlbumsListAdapter(arrayListOf())

    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()

        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewAlbumsList.layoutManager = linearLayoutManager
        recyclerViewAlbumsList.adapter = albumsListAdapter

        mainPresenter.onViewCreated()
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
                mainPresenter.onSearchViewExpand()
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                mainPresenter.onSearchViewCollapse()
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    mainPresenter.onSearchRequest(it)
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
        mainPresenter.onViewStopped()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onViewDestroyed()
    }

    private fun initDagger() {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .mvpModule(MvpModule(this))
            .build()
            .inject(this)
    }


    override fun showServerNotAvailableErrorMessage(error: Throwable) {
        Toast.makeText(this, getString(R.string.server_not_available_error_message, error.toString()), Toast.LENGTH_SHORT).show()
    }

    private fun setSearchViewHint(searchView: SearchView) {
        searchView.queryHint = getString(R.string.search_field_hint)
    }

}
