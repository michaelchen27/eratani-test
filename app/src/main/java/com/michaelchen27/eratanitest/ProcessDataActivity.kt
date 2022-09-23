package com.michaelchen27.eratanitest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.michaelchen27.eratanitest.adapter.MovieAdapter
import com.michaelchen27.eratanitest.data.vo.Movie
import com.michaelchen27.eratanitest.data.vo.MovieItem
import java.io.InputStream

class ProcessDataActivity : AppCompatActivity(), View.OnClickListener,
    BottomSheetFragment.OnFilterListener {
    private val TAG = ProcessDataActivity::class.simpleName
    private lateinit var movieList: ArrayList<MovieItem>
    private lateinit var rvMovieList: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var fabFilter: FloatingActionButton
    private lateinit var bottomSheetFragment: BottomSheetFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_data)
        initView()
        initInteraction()
        readJSON()
        initRV()
    }

    private fun initView() {
        fabFilter = findViewById(R.id.fab_filter)
        rvMovieList = findViewById(R.id.rv_movie_list)
        bottomSheetFragment = BottomSheetFragment(this)
    }

    private fun initInteraction() {
        fabFilter.setOnClickListener(this)
    }

    private fun initRV() {
        rvMovieList.layoutManager =
            LinearLayoutManager(this)
        movieAdapter = MovieAdapter(movieList, this)
        rvMovieList.adapter = movieAdapter
    }


    private fun readJSON() {
        val json: String?

        try {
            val inputStream: InputStream =
                applicationContext!!.resources.openRawResource(R.raw.movies)
            json = inputStream.bufferedReader().use { it.readText() }

            val gson = Gson()
            val movies = gson.fromJson(json, Movie::class.java)
            movieList = movies
            Log.d(TAG, "readJSON: $movieList")

        } catch (e: Exception) {
            Log.e(TAG, "readJSON: $e")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_filter -> {
                if (!bottomSheetFragment.isAdded) {
                    bottomSheetFragment.show(
                        supportFragmentManager,
                        "BottomSheetFragment"
                    )
                }
            }
        }
    }

    override fun onFilter(categoryName: String, sortType: Int) {
        var moviesFilteredByCategory =
            if (categoryName.isNotEmpty()) movieList.filter { it.category.contains(categoryName) } else movieList


        if (sortType != 0) {
            if (sortType == R.id.chip_asc) {
                moviesFilteredByCategory = moviesFilteredByCategory.sortedBy { it.downloadCount }.toList()
            } else {
                moviesFilteredByCategory =
                    moviesFilteredByCategory.sortedByDescending { it.downloadCount }.toList()
            }

        }
        movieAdapter.updateData(moviesFilteredByCategory as ArrayList<MovieItem>)
    }

}