package com.anshmidt.itunesalbums.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.network.models.Album
import kotlinx.android.synthetic.main.album_card_search_results.view.*

class AlbumsListAdapter(
    var albums: ArrayList<Album>,
    val albumClickListener: AlbumClickListener
) : RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {


    interface AlbumClickListener {
        fun onAlbumClick(position: Int, album: Album)
    }

    inner class ViewHolder(view: View, albumClickListener: AlbumClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val imageAlbumArtwork = view.imageAlbumArtworkSearchResults
        private val textArtistName = view.textArtistNameSearchResults
        private val textAlbumName = view.textAlbumNameSearchResults

        fun bind(album: Album) {
            textAlbumName.text = album.albumName
            textArtistName.text = album.artistName

        }

        override fun onClick(view: View?) {
            val itemPosition = adapterPosition
            albumClickListener.onAlbumClick(itemPosition, albums[itemPosition])
        }
    }

    fun updateAlbums(newAlbums: List<Album>) {
        albums.clear()
        albums.addAll(newAlbums)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.album_card_search_results, parent, false),
            albumClickListener
        )

    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
    }

}