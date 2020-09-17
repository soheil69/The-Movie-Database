package com.example.themoviedb.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.core.model.Movie
import com.example.themoviedb.R
import com.example.themoviedb.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.primary_movie_info.*

class DetailFragment : BaseFragment() {

    private val sharedDetailViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[SharedDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh.setOnRefreshListener { swipeRefresh.isRefreshing = false }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedDetailViewModel.detailFragmentState.observe(viewLifecycleOwner) { render(it) }
        sharedDetailViewModel.movie.observe(viewLifecycleOwner) { showMovie(it) }
    }

    private fun render(state: DetailFragmentState) {
        when (state) {
            is DetailFragmentState.ShowInitialViewState -> showInitialView()
            is DetailFragmentState.ShowMovieViewState -> showMovieView()
        }
    }

    private fun showInitialView() {
        messageTxt.visibility = View.VISIBLE
        guideTxt.visibility = View.VISIBLE
        imagesLayout.visibility = View.GONE
        movieTitle.visibility = View.GONE
        divider1.visibility = View.GONE
        releaseDateLabel.visibility = View.GONE
        releaseDate.visibility = View.GONE
        divider2.visibility = View.GONE
        overview.visibility = View.GONE
        divider3.visibility = View.GONE
        ratingLabel.visibility = View.GONE
        userRating.visibility = View.GONE
        divider4.visibility = View.GONE
        favoriteFab.hide()
    }

    private fun showMovieView() {
        messageTxt.visibility = View.GONE
        guideTxt.visibility = View.GONE
        imagesLayout.visibility = View.VISIBLE
        movieTitle.visibility = View.VISIBLE
        divider1.visibility = View.VISIBLE
        releaseDateLabel.visibility = View.VISIBLE
        releaseDate.visibility = View.VISIBLE
        divider2.visibility = View.VISIBLE
        overview.visibility = View.VISIBLE
        divider3.visibility = View.VISIBLE
        ratingLabel.visibility = View.VISIBLE
        userRating.visibility = View.VISIBLE
        divider4.visibility = View.VISIBLE
        favoriteFab.show()
    }

    private fun showMovie(movie: Movie) {
        movieTitle.text = movie.title
        releaseDate.text = movie.releaseDate
        overview.text = movie.overview
        userRating.text = resources.getString(R.string.user_rating_format)
            .format(movie.voteAverage, movie.voteCount)

        Glide.with(this).load(movie.backdropPath).into(backdropImage)
        Glide.with(this).load(movie.posterPath).into(posterImage)
    }
}