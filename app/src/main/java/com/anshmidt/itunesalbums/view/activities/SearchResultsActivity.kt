package com.anshmidt.itunesalbums.view.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.network.models.Album
import com.anshmidt.itunesalbums.view.AlbumsListAdapter
import kotlinx.android.synthetic.main.albums_list.*

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val albumsListAdapter = AlbumsListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
//        handleIntent(intent)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewAlbumsList.layoutManager = linearLayoutManager
        recyclerViewAlbumsList.adapter = albumsListAdapter

//        //temp
//        albumsListAdapter.updateAlbums(arrayListOf(
//            Album("John", "collection1", "track01"),
//            Album("Andy", "collection2", "track02"),
//            Album("Tom", "collection3", "track03")
//        ))
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.options_menu, menu)
//
//        // Associate searchable configuration with the SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        (menu.findItem(R.id.search).actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }
//
//        return true
//    }
//
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        handleIntent(intent)
//    }
//
//    private fun handleIntent(intent: Intent) {
//        if (Intent.ACTION_SEARCH == intent.action) {
//            val searchQuery = intent.getStringExtra(SearchManager.QUERY)
//            //use the query to search your data somehow
//            Toast.makeText(this, "Received request: $searchQuery", Toast.LENGTH_LONG).show()
//            setTitle(searchQuery)
//        }
//    }

}