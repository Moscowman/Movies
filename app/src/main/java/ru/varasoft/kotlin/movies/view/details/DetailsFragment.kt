package ru.varasoft.kotlin.movies.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.varasoft.kotlin.movies.databinding.FragmentDetailsBinding
import ru.varasoft.kotlin.movies.model.MovieInListDTO

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieBundle: MovieInListDTO

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.getRoot()
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

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment = DetailsFragment().apply { arguments = bundle }
    }
}