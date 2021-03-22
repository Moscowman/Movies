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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.varasoft.kotlin.movies.databinding.FragmentDetailsBinding
import ru.varasoft.kotlin.movies.model.MovieInListDTO

class DetailsFragment : Fragment() {
    private val MOVIE_EXTRA = "ru.varasoft.kotlin.movies.model.extra.MOVIE"
    val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
    val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
    val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
    val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
    val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
    val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
    val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
    val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
    val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
    private val PROCESS_ERROR = "Обработка ошибки"
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

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
                    var movieInListDTO: MovieInListDTO? =
                        intent.getParcelableExtra(MOVIE_EXTRA)
                    if (movieInListDTO != null)
                        displayMovie(movieInListDTO)
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
        val movie = arguments?.getParcelable<MovieInListDTO>(BUNDLE_EXTRA)
        if (movie != null) {
            displayMovie(movie)
        }
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayMovie(movie: MovieInListDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            movieOriginalName.text = movie.original_title
            movieRussianName.text = movie.title
            rating.text = "${movie.vote_average}"
            releaseDate.text = movie.release_date
            plot.text = movie.overview
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}