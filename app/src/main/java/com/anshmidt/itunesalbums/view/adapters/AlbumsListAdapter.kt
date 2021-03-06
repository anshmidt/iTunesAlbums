package com.anshmidt.itunesalbums.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.network.models.Album
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.album_card_main.view.*

class AlbumsListAdapter(
    val albumClickListener: AlbumClickListener
) : RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {

    var albums: ArrayList<Album> = arrayListOf()

    interface AlbumClickListener {
        fun onAlbumClick(position: Int, album: Album)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        private val imageAlbumArtwork = view.imageAlbumArtworkSearchResults
        private val textArtistName = view.textArtistNameSearchResults
        private val textAlbumName = view.textAlbumNameSearchResults

        fun bind(album: Album) {
            textAlbumName.text = album.albumName
            textArtistName.text = album.artistName
            showArtwork(album)
        }

        private fun showArtwork(album: Album) {
            Glide.with(itemView)
                .load(album.smallArtworkUrl)
                .centerCrop()
                .skipMemoryCache(false)  // caching of the images is enabled
                .placeholder(R.drawable.album_artwork_placeholder)
                .error(R.drawable.album_artwork_placeholder)
                .fallback(R.drawable.album_artwork_placeholder)
                .into(imageAlbumArtwork)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.album_card_main, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
    }


}