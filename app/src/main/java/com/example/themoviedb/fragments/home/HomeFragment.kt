package com.example.themoviedb.fragments.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.example.themoviedb.adapters.loading.LoadingAdapter
import com.example.themoviedb.adapters.movie.MovieAdapter
import com.example.themoviedb.dialogs.date.DateDialogFragment
import com.example.themoviedb.fragments.BaseFragment
import com.example.themoviedb.fragments.detail.DetailFragmentState
import com.example.themoviedb.fragments.detail.SharedDetailViewModel
import com.example.themoviedb.util.withLoadStateAll
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    private val homeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private val sharedHomeViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[SharedHomeViewModel::class.java]
    }

    private val sharedDetailViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[SharedDetailViewModel::class.java]
    }

    @Inject
    internal lateinit var dateFormat: SimpleDateFormat

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.homeFragmentState.observe(viewLifecycleOwner) { render(it) }
        homeViewModel.pagingData.observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        sharedHomeViewModel.singleEventDateFilter.observe(viewLifecycleOwner) { singleEvent ->
            singleEvent.getUntouchedValue()?.let {
                setupAdapterAndRecyclerView()
                homeViewModel.start(it.gte, it.lte)
                sharedDetailViewModel.setState(DetailFragmentState.ShowInitialViewState)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return when {
            super.onBackPressed() -> true
            homeViewModel.homeFragmentState.value != HomeFragmentState.ShowInitialViewState -> {
                homeViewModel.setState(HomeFragmentState.ShowInitialViewState)
                sharedDetailViewModel.setState(DetailFragmentState.ShowInitialViewState)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        movieRecyclerView.adapter = null
        super.onDestroyView()
    }

    private fun render(state: HomeFragmentState) {
        when (state) {
            is HomeFragmentState.ShowInitialViewState -> showInitialView()
            is HomeFragmentState.ShowRecyclerViewState -> showRecyclerView()
        }
    }

    private fun showInitialView() {
        welcomeTxt.visibility = View.VISIBLE
        descriptionTxt.visibility = View.VISIBLE
        startBtn.visibility = View.VISIBLE
        movieRecyclerView.visibility = View.GONE
        fab.hide()
    }

    private fun showRecyclerView() {
        welcomeTxt.visibility = View.GONE
        descriptionTxt.visibility = View.GONE
        startBtn.visibility = View.GONE
        movieRecyclerView.visibility = View.VISIBLE
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            fab.show()
        else fab.hide()
    }

    private fun setupView() {
        movieRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.custom_divider)!!
        )
        movieRecyclerView.addItemDecoration(divider)

        setupAdapterAndRecyclerView()

        startBtn.setOnClickListener {
            setupAdapterAndRecyclerView()
            homeViewModel.start(null, Date())
            homeViewModel.setState(HomeFragmentState.ShowRecyclerViewState)
            sharedDetailViewModel.setState(DetailFragmentState.ShowInitialViewState)
        }

        fab.setOnClickListener {
            val dateDialogFragment = DateDialogFragment()
            dateDialogFragment.show(parentFragmentManager, "DateDialogFragment")
        }
    }

    private fun setupAdapterAndRecyclerView() {
        movieAdapter = MovieAdapter().apply {
            setOnItemClickListener { _, movie, _ ->
                sharedDetailViewModel.postMovie(movie)
                sharedDetailViewModel.setState(DetailFragmentState.ShowMovieViewState)
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
            }
        }

        movieRecyclerView.adapter = movieAdapter.withLoadStateAll(
            refresh = LoadingAdapter(movieAdapter::refresh),
            header = LoadingAdapter(movieAdapter::retry),
            footer = LoadingAdapter(movieAdapter::retry)
        )
    }
}