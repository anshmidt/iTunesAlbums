package com.anshmidt.itunesalbums.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.network.models.Album
import kotlinx.android.synthetic.main.album_card.view.*

class AlbumsListAdapter(var albums: ArrayList<Album>) : RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageAlbumArtwork = view.imageAlbumArtwork
        private val textArtistName = view.textArtistName
        private val textAlbumName = view.textAlbumName

        fun bind(album: Album) {
            textAlbumName.text = album.collectionName
            textArtistName.text = album.artistName
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
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_card, parent, false))
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
    }

}