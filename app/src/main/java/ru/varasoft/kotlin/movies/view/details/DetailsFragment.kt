package ru.varasoft.kotlin.movies.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.databinding.FragmentDetailsBinding
import ru.varasoft.kotlin.movies.model.Movie

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        if (movie != null) binding.apply {
            movieOriginalName.text = movie.originalName
            movieRussianName.text = movie.russianName
            genres.text = movie.russianName
            length.text = "$movie.length мин."
            rating.text = "$movie.rating (${movie.usersRated})"
            budget.text = "Бюджет: ${movie.budget}"
            revenue.text = "Сборы: ${movie.revenue}"
            releaseDate.text = movie.releaseDate.toString()
            plot.text = movie.plot
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment = DetailsFragment().apply { arguments = bundle }
    }
}