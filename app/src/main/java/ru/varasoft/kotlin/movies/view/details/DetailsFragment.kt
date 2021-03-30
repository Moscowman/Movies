package ru.varasoft.kotlin.movies.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.app.AppState
import ru.varasoft.kotlin.movies.databinding.FragmentDetailsBinding
import ru.varasoft.kotlin.movies.model.MovieDTO
import ru.varasoft.kotlin.movies.repository.DetailsService
import ru.varasoft.kotlin.movies.utils.showSnackBar
import ru.varasoft.kotlin.movies.viewmodel.DetailsViewModel

val ACTION_LOAD_MOVIE = "ru.varasoft.kotlin.movies.model.action.load_movies"
val MOVIE_EXTRA = "ru.varasoft.kotlin.movies.model.extra.MOVIE"
val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var movieBundle: MovieDTO? = null

    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }


    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> {
                    var movieDTO: MovieDTO? =
                        intent.getParcelableExtra(MOVIE_EXTRA)
                    if (movieDTO != null)
                        displayMovie(movieDTO)
                }
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieBundle = arguments?.getParcelable(MOVIE_EXTRA) ?: MovieDTO(-1)
        viewModel.detailsLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMovieFromRemoteSource(movieBundle!!.id ?: -1)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                displayMovie(appState.movieData[0])
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        if (movieBundle != null)
                            viewModel.getMovieFromRemoteSource(movieBundle!!.id ?: -1)
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayMovie(movie: MovieDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            includedLoadingLayout.loadingLayout.visibility = View.GONE
            movieOriginalName.text = movie.original_title
            movieRussianName.text = movie.title
            rating.text = "${movie.vote_average}"
            releaseDate.text = movie.release_date
            plot.text = movie.overview
        }
        Glide.with(this)
            .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${movie.poster_path}")
            .into(binding.imageView);
    }

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}