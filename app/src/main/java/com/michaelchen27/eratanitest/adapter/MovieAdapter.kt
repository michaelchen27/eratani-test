package com.michaelchen27.eratanitest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michaelchen27.eratanitest.R
import com.michaelchen27.eratanitest.data.vo.MovieItem

class MovieAdapter(
    private var movieList: ArrayList<MovieItem>,
    private val context: Context
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tvTitle: TextView = view.findViewById(R.id.tv_title)
        private var tvCategory: TextView = view.findViewById(R.id.tv_category)
        private var tvDownloadCount: TextView = view.findViewById(R.id.tv_download_count)


        fun bind(movieItem: MovieItem?, context: Context) {
            val title = movieItem?.title
            val category = movieItem?.category
            val downloadCount = movieItem?.downloadCount.toString()

            tvTitle.text = title
            tvCategory.text = category
            tvDownloadCount.text = downloadCount
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position], context)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(filteredMovieList: ArrayList<MovieItem>) {
        movieList = filteredMovieList
        notifyDataSetChanged()
    }

}