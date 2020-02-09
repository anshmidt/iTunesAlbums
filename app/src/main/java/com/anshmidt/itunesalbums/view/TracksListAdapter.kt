package com.anshmidt.itunesalbums.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anshmidt.itunesalbums.R
import com.anshmidt.itunesalbums.network.models.Track
import kotlinx.android.synthetic.main.track_in_album_info.view.*

class TracksListAdapter(val tracks: ArrayList<Track>) : RecyclerView.Adapter<TracksListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTrackName = view.textTrackNameAlbumInfo

        fun bind(track: Track) {
            textTrackName.text = track.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.track_in_album_info, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    fun updateTracks(newTracks: List<Track>) {
        tracks.clear()
        tracks.addAll(newTracks)
        notifyDataSetChanged()
    }
}